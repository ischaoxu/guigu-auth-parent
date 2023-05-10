package com.wiselzx.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wiselzx.model.system.SysRoleMenu;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SysRoleMenuMapper extends BaseMapper<SysRoleMenu> {
    /**
     * 保存角色菜单数据
     * @param roleId
     * @param menuIdList
     * @return
     */
    int insertRoleMenu(@Param("roleId") Long roleId,@Param("menuIdList") List<Long> menuIdList);

}

