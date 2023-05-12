package com.wiselzx.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wiselzx.model.system.SysMenu;

import java.util.List;

/**
 * <p>
 * 菜单表 Mapper 接口
 * </p>
 *
 * @author wiseLzx
 * @since 2023-05-08
 */
public interface SysMenuMapper extends BaseMapper<SysMenu> {
    /**
     * 根据用户id
     * 获取用户对应的菜单信息
     * @param userId
     * @return
     */
    List<SysMenu> selectMenusInfoByUserId(Long userId);
}
