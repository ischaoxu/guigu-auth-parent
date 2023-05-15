package com.wiselzx.system.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.wiselzx.common.helper.MenuHelper;
import com.wiselzx.common.helper.RouterHelper;
import com.wiselzx.common.result.Result;
import com.wiselzx.common.util.MD5;
import com.wiselzx.model.system.SysMenu;
import com.wiselzx.model.system.SysUser;
import com.wiselzx.model.vo.LoginVo;
import com.wiselzx.model.vo.RouterVo;
import com.wiselzx.system.exception.MyException;
import com.wiselzx.system.service.SysUserService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * <p>
 * 后台登录登出
 * </p>
 */
@Api(tags = "后台登录管理")
@RestController
@RequestMapping("/admin/system/index")
public class IndexController {

    @Autowired
    private SysUserService sysUserService;
    @Autowired
    private RedisTemplate redisTemplate;


    /**
     * 登录
     *
     * @return
     */
    @PostMapping("/login")
    public Result login(@RequestBody LoginVo loginVo) {

        SysUser sysUser = sysUserService.getOne(new LambdaQueryWrapper<SysUser>().eq(SysUser::getUsername, loginVo.getUsername()));
        if (sysUser == null) {
            throw new MyException("没有该用户");
        }
        if (!sysUser.getPassword().equals(MD5.encrypt(loginVo.getPassword()))) {
            throw new MyException("用户名密码错误");
        }
        if (sysUser.getStatus() == 0) {
            throw new MyException("用户已经被禁用");

        }
        String token = UUID.randomUUID().toString().replaceAll("-", "");
        redisTemplate.opsForValue().set(token,sysUser,1, TimeUnit.MINUTES);
        HashMap<String, Object> map = new HashMap<>();
        map.put("token",token);
        return Result.ok(map);
    }
    /**
     * 获取用户信息
     * @return
     */
    @GetMapping("/info")
    public Result info(HttpServletRequest request) {
        String token = request.getHeader("token");
        SysUser sysUser = (SysUser)redisTemplate.opsForValue().get(token);
        List<SysMenu> sysMenuList = sysUserService.getMenusInfoByUserId(sysUser.getId());
        List<RouterVo> routerVoList = RouterHelper.buildRouters(MenuHelper.buildTree(sysMenuList));
        List<String> buttons = new ArrayList<>();
        sysMenuList.forEach(sysMenu -> {
            if (sysMenu.getType() == 2) {
                buttons.add(sysMenu.getPerms());
            }
        });
        Map<String, Object> map = new HashMap<>();
//        map.put("roles",new HashSet<>());
        map.put("name",sysUser.getName());
        map.put("buttons", buttons);
        map.put("routers", routerVoList);
        map.put("avatar","https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif");
        return Result.ok(map);
    }
    /**
     * 退出
     * @return
     */
    @PostMapping("/logout")
    public Result logout(HttpServletRequest request){
        String token = request.getHeader("token");
        redisTemplate.delete(token);
        return Result.ok();
    }

}
