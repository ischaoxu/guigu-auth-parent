package com.wiselzx.system.controller;


import com.wiselzx.common.result.Result;
import com.wiselzx.model.system.SysMenu;
import com.wiselzx.model.vo.AssignMenuVo;
import com.wiselzx.system.service.SysMenuService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 菜单表 前端控制器
 * </p>
 *
 * @author wiseLzx
 * @since 2023-05-08
 */
@Api(tags = "菜单")
@RestController
@RequestMapping("/admin/system/sysMenu")
public class SysMenuController {

    @Autowired
    private SysMenuService sysMenuService;

    @ApiOperation(value = "保存角色菜单数据")
    @PostMapping("/doAssign")
    public  Result changeRoelMenu(@RequestBody AssignMenuVo assignMenuVo) {
        Boolean ischange = sysMenuService.changeRoleMenu(assignMenuVo);
        if (ischange) {
            return  Result.ok();
        }
        return Result.fail();
    }

    @ApiOperation(value = "根据角色id获取菜单数据")
    @GetMapping("/toAssign/{id}")
    public Result toAssign(@PathVariable("id") Long roleId) {
        List<SysMenu> sysMenuList = sysMenuService.findRoleMenu(roleId);
        if (!CollectionUtils.isEmpty(sysMenuList)) {
            return  Result.ok(sysMenuList);
        }
        return Result.fail();
    }

    @ApiOperation(value = "添加菜单")
    @PostMapping("/save")
    public Result addMenu(@RequestBody SysMenu sysMenu) {
        boolean save = sysMenuService.save(sysMenu);
        if (save) return Result.ok();
        return Result.fail();
    }
    @ApiOperation(value = "根据id删除菜单")
    @DeleteMapping("/remove/{id}")
    public Result removeMenu(@PathVariable("id") Long id) {
        boolean b = sysMenuService.delById(id);
        if (b) return Result.ok();
        return Result.fail();
    }
    @ApiOperation(value = "修改菜单")
    @PutMapping("/update")
    public Result removeMenu(@RequestBody SysMenu sysMenu) {
        boolean b = sysMenuService.updateById(sysMenu);
        if (b) return Result.ok();
        return Result.fail();
    }
    @ApiOperation(value = "根据id获取菜单")
    @GetMapping("/{id}")
    public Result findMenuById(@PathVariable("id") Long id) {
        SysMenu sysMenu = sysMenuService.getById(id);
        return Result.ok(sysMenu);
    }
    @ApiOperation(value = "获取菜单")
    @GetMapping("/findNodes")
    public Result findMenu() {
        List<SysMenu> list = sysMenuService.findTree();
        return Result.ok(list);
    }

}

