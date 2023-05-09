package com.wiselzx.system.controller;


import com.wiselzx.common.result.Result;
import com.wiselzx.model.system.SysMenu;
import com.wiselzx.system.service.SysMenuService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
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

    @ApiOperation(value = "添加菜单")
    @PostMapping
    public Result addMenu(@RequestBody SysMenu sysMenu) {
        boolean save = sysMenuService.save(sysMenu);
        if (save) return Result.ok();
        return Result.fail();
    }
    @ApiOperation(value = "根据id删除菜单")
    @DeleteMapping("/{id}")
    public Result removeMenu(@PathVariable("id") Long id) {
        boolean b = sysMenuService.removeById(id);
        if (b) return Result.ok();
        return Result.fail();
    }
    @ApiOperation(value = "修改菜单")
    @PutMapping
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
    @GetMapping
    public Result findMenu() {
        List<SysMenu> list = sysMenuService.findTree();
        return Result.ok(list);
    }

}

