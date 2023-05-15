package com.wiselzx.system.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wiselzx.common.result.Result;
import com.wiselzx.model.system.SysRole;
import com.wiselzx.model.vo.AssignRoleVo;
import com.wiselzx.model.vo.SysRoleQueryVo;
import com.wiselzx.system.service.SysRoleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

/**
 * <p>
 * 角色 前端控制器
 * </p>
 *
 * @author wiseLzx
 * @since 2023-05-05
 */
@Api(tags = "角色")
@RestController
@RequestMapping("/admin/system/sysRole")
public class SysRoleController {
    @Autowired
    private SysRoleService sysRoleService;

    @ApiOperation(value = "根据用户获取角色数据")
    @GetMapping("/toAssign/{userId}")
    public Result findUserRoles(@PathVariable("userId") Long userId) {
        HashMap<String, List> map = sysRoleService.findUserRoles(userId);
        return Result.ok(map);
    }
    @PreAuthorize("hasAuthority('bnt.sysRole.update')")
    @ApiOperation(value = "根据用户保存分配角色")
    @PostMapping("/doAssign")
    public Result changeUserRole(@RequestBody AssignRoleVo assignRoleVo) {
        boolean isChange = sysRoleService.changeUserRole(assignRoleVo);
        if (isChange) return Result.ok();
        return Result.fail();

    }

    @PreAuthorize("hasAuthority('bnt.sysRole.update')")
    @ApiOperation(value = "修改角色")
    @PutMapping("/update")
    public Result upRole(@RequestBody SysRole sysRole) {
        boolean update = sysRoleService.updateById(sysRole);
        if (update) {
            return Result.ok();
        }
        return Result.fail();
    }
    @PreAuthorize("hasAuthority('bnt.sysRole.remove')")
    @ApiOperation(value = "id删除角色")
    @DeleteMapping("/remove/{id}")
    public Result deleteRole(@PathVariable("id") Long id) {
        boolean remove = sysRoleService.removeById(id);
        if (remove) {
            return Result.ok();
        }
        return Result.fail();
    }
    @PreAuthorize("hasAuthority('bnt.sysRole.remove')")
    @ApiOperation("id批量删除")
    @DeleteMapping("/batchRemove")
    public Result batchDelete(@RequestBody List<Long> idlist) {
        boolean remove = sysRoleService.removeByIds(idlist);
        if (remove) {
            return Result.ok();

        }
        return Result.fail();
    }
    @PreAuthorize("hasAuthority('bnt.sysRole.list')")
    @ApiOperation(value = "id获取角色")
    @GetMapping("/get/{id}")
    public Result showRoleById(@PathVariable("id") Long id) {
        SysRole sysRole = sysRoleService.getById(id);
        return Result.ok(sysRole);
    }
    @PreAuthorize("hasAuthority('bnt.sysRole.add')")
    @ApiOperation(value = "添加角色")
    @PostMapping("/save")
    public Result save(@RequestBody SysRole sysRole) {

        boolean isSave = sysRoleService.save(sysRole);
        if (isSave) {
            return Result.ok();
        }
        return Result.fail();
    }
    @PreAuthorize("hasAuthority('bnt.sysRole.list')")
    @ApiOperation(value = "获取全部角色")
    @GetMapping("/findAll")
    public Result findAll() {

        List<SysRole> list = sysRoleService.list();
        return Result.ok(list);
    }
    @PreAuthorize("hasAuthority('bnt.sysRole.list')")
    @ApiOperation(value = "分页条件查询")
    @GetMapping("/findQueryPage/{current}/{limit}")
    public Result findPage(@PathVariable("current") Long current, @PathVariable("limit") Long limit, SysRoleQueryVo sysRoleQueryVo) {
        Page<SysRole> rolePage = new Page<>(current, limit);
        String roleName = sysRoleQueryVo.getRoleName();

        LambdaQueryWrapper<SysRole> wrapper = new LambdaQueryWrapper<>();
        if (!StringUtils.isEmpty(roleName)) {
            wrapper.like(SysRole::getRoleName, roleName);
        }
        Page<SysRole> sysRolePage = sysRoleService.page(rolePage, wrapper);
        return Result.ok(sysRolePage);
    }
}

