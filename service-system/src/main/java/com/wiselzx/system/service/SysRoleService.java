package com.wiselzx.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.wiselzx.model.system.SysRole;
import com.wiselzx.model.vo.AssignRoleVo;

import java.util.HashMap;
import java.util.List;

/**
 * <p>
 * 角色 服务类
 * </p>
 *
 * @author wiseLzx
 * @since 2023-05-05
 */
public interface SysRoleService extends IService<SysRole> {
    /**
     * 根据用户id查询角色数据
     * @param userId
     * @return
     */
    HashMap<String,List> findUserRoles(Long userId);

    /**
     * 修改用户角色数据
     * @param assignRoleVo
     * @return
     */
    boolean changeUserRole(AssignRoleVo assignRoleVo);
}
