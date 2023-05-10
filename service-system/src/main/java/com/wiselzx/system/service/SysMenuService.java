package com.wiselzx.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.wiselzx.model.system.SysMenu;

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
}
