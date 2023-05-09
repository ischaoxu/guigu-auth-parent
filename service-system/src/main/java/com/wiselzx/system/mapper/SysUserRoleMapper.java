package com.wiselzx.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wiselzx.model.system.SysUserRole;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SysUserRoleMapper extends BaseMapper<SysUserRole> {


    /**
     * 根据用户id清除角色数据
     * @param userId
     * @return
     */
    boolean deleteRolesByUserId(@Param("userId") Long userId);

    /**
     * 根据用户id设置角色数据
     * @param userId
     * @param roleIdList
     * @return
     */
    boolean saveRolesByUserId(@Param("userId")Long userId,@Param("roleIdList") List roleIdList);

}
