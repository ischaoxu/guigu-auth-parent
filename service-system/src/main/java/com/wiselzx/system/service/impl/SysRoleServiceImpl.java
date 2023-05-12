package com.wiselzx.system.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wiselzx.model.system.SysRole;
import com.wiselzx.model.system.SysUserRole;
import com.wiselzx.model.vo.AssignRoleVo;
import com.wiselzx.system.mapper.SysRoleMapper;
import com.wiselzx.system.mapper.SysUserRoleMapper;
import com.wiselzx.system.service.SysRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * <p>
 * 角色 服务实现类
 * </p>
 *
 * @author wiseLzx
 * @since 2023-05-05
 */
@Service
public class SysRoleServiceImpl extends ServiceImpl<SysRoleMapper, SysRole> implements SysRoleService {
    @Autowired
    private SysUserRoleMapper sysUserRoleMapper;

    @Override
    public HashMap<String, List> findUserRoles(Long userId) {
        List<SysRole> roleList = baseMapper.selectRoleListByUserId(userId);
        ArrayList<Long> arrayList = new ArrayList<>();
        roleList.forEach(sysRole -> {
            arrayList.add(sysRole.getId());
        });
        List<SysRole> sysRoleList = baseMapper.selectList(null);
        HashMap<String, List> map = new HashMap<>();
        map.put("userRoleIds", arrayList);
        map.put("allRoles", sysRoleList);
        return map;
    }

    @Override
    @Transactional
    public boolean changeUserRole(AssignRoleVo assignRoleVo) {
        LambdaQueryWrapper<SysUserRole> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SysUserRole::getUserId, assignRoleVo.getUserId());
        int delete = sysUserRoleMapper.delete(wrapper);
        boolean isSave = sysUserRoleMapper.saveRolesByUserId(assignRoleVo.getUserId(), assignRoleVo.getRoleIdList());
        if (isSave) return true;
        return false;
    }
}
