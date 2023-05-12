package com.wiselzx.system.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wiselzx.model.system.SysMenu;
import com.wiselzx.model.system.SysUser;
import com.wiselzx.system.mapper.SysMenuMapper;
import com.wiselzx.system.mapper.SysUserMapper;
import com.wiselzx.system.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 用户表 服务实现类
 * </p>
 *
 * @author wiseLzx
 * @since 2023-05-08
 */
@Service
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements SysUserService {

    @Autowired
    private SysMenuMapper sysMenuMapper;
    @Override
    public List<SysMenu> getMenusInfoByUserId(Long userId) {
        if (userId == 1L) {
            return sysMenuMapper.selectList(null);
        }
        return sysMenuMapper.selectMenusInfoByUserId(userId);
    }
}
