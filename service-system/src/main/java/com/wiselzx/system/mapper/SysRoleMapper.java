package com.wiselzx.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wiselzx.model.system.SysRole;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 角色 Mapper 接口
 * </p>
 *
 * @author wiseLzx
 * @since 2023-05-05
 */
public interface SysRoleMapper extends BaseMapper<SysRole> {
    /**
     * 根据用户id查询角色数据
     * @param userId
     * @return
     */
    List<SysRole> selectRoleListByUserId(@Param("userId") Long userId);
}
