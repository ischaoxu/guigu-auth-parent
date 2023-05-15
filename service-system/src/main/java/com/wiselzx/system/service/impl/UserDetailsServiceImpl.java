package com.wiselzx.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.wiselzx.model.system.SysUser;
import com.wiselzx.system.custom.CustomUser;
import com.wiselzx.system.service.SysMenuService;
import com.wiselzx.system.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    private SysUserService sysUserService;

    @Autowired
    private SysMenuService sysMenuService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        //根据用户名得到用户信息
        LambdaQueryWrapper<SysUser> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SysUser::getUsername,username);
        SysUser sysUser = sysUserService.getOne(wrapper);

        //查询用户权限数据，按钮数据
        List<String> permsList = sysMenuService.findPermsListByUserId(sysUser.getId());
        sysUser.setUserPermsList(permsList);

        //sysUser转换CustomUser
        return new CustomUser(sysUser, Collections.emptyList());
    }
}
