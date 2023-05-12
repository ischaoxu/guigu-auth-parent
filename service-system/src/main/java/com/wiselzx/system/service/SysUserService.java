package com.wiselzx.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.wiselzx.model.system.SysMenu;
import com.wiselzx.model.system.SysUser;

import java.util.List;

/**
 * <p>
 * 用户表 服务类
 * </p>
 *
 * @author wiseLzx
 * @since 2023-05-08
 */
public interface SysUserService extends IService<SysUser> {
    /**
     * 根据用户id
     * 获取用户对应的菜单信息
     * @param userId
     * @return
     */
    List<SysMenu> getMenusInfoByUserId(Long userId);
}
