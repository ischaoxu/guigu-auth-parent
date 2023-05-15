package com.wiselzx.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.wiselzx.model.system.SysMenu;
import com.wiselzx.model.vo.AssignMenuVo;

import java.util.List;

/**
 * <p>
 * 菜单表 服务类
 * </p>
 *
 * @author wiseLzx
 * @since 2023-05-08
 */
public interface SysMenuService extends IService<SysMenu> {
    /**
     * 获取菜单树
     * @return
     */
    List<SysMenu> findTree();

    /**
     * 根据id删除菜单
     * @param id
     * @return
     */
    boolean delById(Long id);

    /**
     * 根据角色id获取菜单数据
     * @param roleId
     * @return
     */
    List<SysMenu> findRoleMenu(Long roleId);

    /**
     * 保存角色菜单数据
     * @param assignMenuVo
     * @return
     */
    Boolean changeRoleMenu(AssignMenuVo assignMenuVo);

    /**
     * 根据用户id获取所有对应角色的按钮权限
     * @param id
     * @return
     */
    List<String> findPermsListByUserId(Long id);
}
