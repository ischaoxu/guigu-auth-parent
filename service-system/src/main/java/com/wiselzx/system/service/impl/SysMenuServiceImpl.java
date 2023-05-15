package com.wiselzx.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wiselzx.common.helper.MenuHelper;
import com.wiselzx.common.result.ResultCodeEnum;
import com.wiselzx.model.system.SysMenu;
import com.wiselzx.model.system.SysRoleMenu;
import com.wiselzx.model.vo.AssignMenuVo;
import com.wiselzx.system.exception.MyException;
import com.wiselzx.system.mapper.SysMenuMapper;
import com.wiselzx.system.mapper.SysRoleMenuMapper;
import com.wiselzx.system.service.SysMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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
    @Autowired
    private SysRoleMenuMapper sysRoleMenuMapper;

    @Override
    @Transactional
    public Boolean changeRoleMenu(AssignMenuVo assignMenuVo) {
        int i = sysRoleMenuMapper.delete(new LambdaQueryWrapper<SysRoleMenu>().eq(SysRoleMenu::getRoleId,assignMenuVo.getRoleId()));
        if (!CollectionUtils.isEmpty(assignMenuVo.getMenuIdList()))
            sysRoleMenuMapper.insertRoleMenu(assignMenuVo.getRoleId(),assignMenuVo.getMenuIdList());
        return true;
    }

    @Override
    public List<String> findPermsListByUserId(Long id) {
        //管理员，查询所有权限数据
        List<SysMenu> sysMenuList = null;
        if(id == 1L) {
            sysMenuList = baseMapper.selectList(null);
        } else {//不是管理员，调用方法，根据userid查询权限数据
            sysMenuList = baseMapper.selectMenusInfoByUserId(id);
        }

        //List<SysMenu> ==  List<String>  perms值
        //stream流
        List<String> permsList = sysMenuList.stream()
                .filter(item -> item.getType() == 2)
                .map(item -> item.getPerms())
                .collect(Collectors.toList());

        return permsList;
    }

    @Override
    public List<SysMenu> findRoleMenu(Long roleId) {
        LambdaQueryWrapper<SysRoleMenu> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SysRoleMenu::getRoleId,roleId);
        List<SysRoleMenu> sysRoleMenuList = sysRoleMenuMapper.selectList(wrapper);
        List<SysMenu> sysMenuList = baseMapper.selectList(new LambdaQueryWrapper<SysMenu>().eq(SysMenu::getStatus, 1));
        List<Long> longList = new ArrayList<>();
        sysRoleMenuList.forEach(item->{
            longList.add(item.getMenuId());
        });
        sysMenuList.forEach(item->{
            if (longList.contains(item.getId())) {
                item.setSelect(true);
            } else {
                item.setSelect(false);
            }
        });
        List<SysMenu> tree = MenuHelper.buildTree(sysMenuList);
        return tree;
    }





    @Override
    public List<SysMenu> findTree() {
        List<SysMenu> sysMenuList = baseMapper.selectList(null);
        List<SysMenu> sysMenuTree = MenuHelper.buildTree(sysMenuList);
//        if (CollectionUtils.isEmpty(sysMenuList)) return null;
//        ArrayList<SysMenu> sysMenuTree = new ArrayList<>();
//        sysMenuList.forEach(item->{
//            if (item.getParentId() == 0) {
//                item.setChildren(new ArrayList<SysMenu>());
//                item.getChildren().add(finChildren(item,sysMenuList));
//                sysMenuTree.add(item);
//            }
//        });
        return sysMenuTree;
    }
//    SysMenu finChildren(SysMenu sysMenu,List<SysMenu> sysMenuList) {
//        sysMenu.setChildren(new ArrayList<SysMenu>());
//        sysMenuList.forEach(item ->{
//            if (sysMenu.getId().longValue() == item.getParentId()) {
//                sysMenu.getChildren().add(finChildren(item,sysMenuList));
//            }
//        });
//
//        return sysMenu;

//    }

    @Override
    public boolean delById(Long id) {
        Integer count = baseMapper.selectCount(new LambdaQueryWrapper<SysMenu>().eq(SysMenu::getParentId, id));
        if (count > 0) throw  new MyException(ResultCodeEnum.NODE_ERROR.getMessage());
        int i = baseMapper.deleteById(id);
        if (i > 0) return true;
        return false;
    }


}
