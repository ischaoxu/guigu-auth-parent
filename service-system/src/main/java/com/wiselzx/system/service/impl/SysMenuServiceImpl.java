package com.wiselzx.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wiselzx.common.helper.MenuHelper;
import com.wiselzx.common.result.ResultCodeEnum;
import com.wiselzx.model.system.SysMenu;
import com.wiselzx.system.exception.MyException;
import com.wiselzx.system.mapper.SysMenuMapper;
import com.wiselzx.system.service.SysMenuService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 菜单表 服务实现类
 * </p>
 *
 * @author wiseLzx
 * @since 2023-05-08
 */
@Service
public class SysMenuServiceImpl extends ServiceImpl<SysMenuMapper, SysMenu> implements SysMenuService {

    @Override
    public List<SysMenu> findTree() {
        List<SysMenu> sysMenuList = baseMapper.selectList(null);
        List<SysMenu> sysMenus = MenuHelper.buildTree(sysMenuList);
        return sysMenus;
    }

    @Override
    public boolean delById(Long id) {
        Integer count = baseMapper.selectCount(new LambdaQueryWrapper<SysMenu>().eq(SysMenu::getParentId, id));
        if (count > 0) throw  new MyException(ResultCodeEnum.NODE_ERROR.getMessage());
        int i = baseMapper.deleteById(id);
        if (i > 0) return true;
        return false;
    }
}
