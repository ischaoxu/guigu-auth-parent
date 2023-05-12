package com.wiselzx.system.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wiselzx.common.result.Result;
import com.wiselzx.common.util.MD5;
import com.wiselzx.model.base.BaseEntity;
import com.wiselzx.model.system.SysUser;
import com.wiselzx.model.vo.SysUserQueryVo;
import com.wiselzx.system.exception.MyException;
import com.wiselzx.system.service.SysUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 用户表 前端控制器
 * </p>
 *
 * @author wiseLzx
 * @since 2023-05-08
 */
@Api(tags = "用户")
@RestController
@RequestMapping("/admin/system/sysUser")
public class SysUserController {
    @Autowired
    private SysUserService userService;

    /**
     * 查看所有用户
     *
     * @return
     */
    @ApiOperation(value = "查看所有用户")
    @GetMapping
    public Result findAll() {
        return Result.ok(userService.list());
    }

    /**
     * 查看所有用户(分页)
     *
     * @return
     */
    @ApiOperation(value = "分页查看所有用户")
    @GetMapping("/{page}/{limit}")
    public Result findPageAll(@PathVariable("page") Integer page, @PathVariable("limit") Integer limit, SysUserQueryVo sysUserQueryVo) {
        LambdaQueryWrapper<SysUser> wrapper = new LambdaQueryWrapper<>();
        String createTimeBegin = sysUserQueryVo.getCreateTimeBegin();
        String createTimeEnd = sysUserQueryVo.getCreateTimeEnd();
        String keyword = sysUserQueryVo.getKeyword();
        if (!StringUtils.isEmpty(keyword)) {
            wrapper = wrapper.like(SysUser::getUsername, keyword).or();
            wrapper.like(SysUser::getName, keyword).or().like(SysUser::getPhone, keyword);
        }
        if (!StringUtils.isEmpty(createTimeBegin) && !StringUtils.isEmpty(createTimeEnd)) {
            wrapper.between(BaseEntity::getCreateTime, createTimeBegin, createTimeEnd);
        }
        Page<SysUser> userPage = new Page<>(page, limit);
        Page<SysUser> sysUserPage = userService.page(userPage, wrapper);
        return Result.ok(sysUserPage);
    }

    /**
     * 根据id获取用户
     *
     * @param id
     * @return
     */
    @ApiOperation(value = "根据id获取用户")
    @GetMapping("/get/{id}")
    public Result findUserById(@PathVariable("id") Long id) {
        return Result.ok(userService.getById(id));
    }


    /**
     * 保存用户
     *
     * @param sysUser
     * @return
     */
    @ApiOperation(value = "保存用户")
    @PostMapping("/save")
    public Result saveUser(@RequestBody SysUser sysUser) {
        sysUser.setStatus(0);
        sysUser.setHeadUrl("https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif");
        sysUser.setPassword(MD5.encrypt(sysUser.getPassword()));
        boolean save = userService.save(sysUser);
        if (save) {
            return Result.ok();
        }
        return Result.fail();
    }

    /**
     * 根据id修改用户信息
     *
     * @param sysUser
     * @return
     */
    @ApiOperation(value = "根据id修改用户信息")
    @PutMapping("/update")
    private Result updateUser(@RequestBody SysUser sysUser) {
        boolean b = userService.updateById(sysUser);
        if (b) {
            return Result.ok();
        }
        return Result.fail();
    }

    /**
     * 根据id删除用户
     *
     * @param id
     * @return
     */
    @ApiOperation(value = "根据id删除用户")
    @DeleteMapping("/remove/{id}")
    private Result removeUser(@PathVariable("id") Long id) {
        boolean b = userService.removeById(id);
        if (b) {
            return Result.ok();
        }
        return Result.fail();
    }

    @ApiOperation(value = "修改状态")
    @GetMapping("/updateStatus/{id}/{status}")
    public Result changeStatus( @PathVariable("id") Long id ,
                                @PathVariable("status") Integer status) {
        LambdaQueryWrapper<SysUser> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(BaseEntity::getId, id);
        if (status != 0 && status != 1) {
            throw  new MyException("错误的状态数据");
        }
        SysUser user = new SysUser();
        user.setStatus(status);
        boolean update = userService.update(user, wrapper);
        if (update) {
            return Result.ok();
        }
        return Result.fail().message("修改失败");

    }

}

