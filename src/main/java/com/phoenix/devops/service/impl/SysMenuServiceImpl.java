package com.phoenix.devops.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.mybatisflex.core.query.QueryWrapper;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import com.phoenix.devops.common.SelectCommon;
import com.phoenix.devops.entity.SysMenu;
import com.phoenix.devops.mapper.SysMenuMapper;
import com.phoenix.devops.model.vo.SysMenuVO;
import com.phoenix.devops.service.ISysMenuService;
import com.phoenix.devops.service.ISysRoleMenuService;
import com.phoenix.devops.utils.TreeUtil;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.phoenix.devops.entity.table.SysRoleMenuTableDef.SYS_ROLE_MENU;

/**
 * 服务层实现。
 *
 * @author wjj-phoenix
 * @since 2025-03-11
 */
@Service
public class SysMenuServiceImpl extends ServiceImpl<SysMenuMapper, SysMenu> implements ISysMenuService {
    @Resource
    private ISysRoleMenuService roleMenuService;

    @Override
    public List<SysMenuVO> fetchAllSysMenus(String condition) {
        List<SysMenu> menus = SelectCommon.findAll(condition, mapper);
        return TreeUtil.generateTrees(BeanUtil.copyToList(menus, SysMenuVO.class));
    }

    @Override
    public long addSysMenu(SysMenuVO menuVO) {
        SysMenu menu = BeanUtil.toBean(menuVO, SysMenu.class);
        if (!this.save(menu)) {
            throw new IllegalStateException("添加菜单信息失败!");
        }
        return menu.getId();
    }

    @Override
    public boolean modSysMenuById(SysMenuVO menuVO) {
        SysMenu menu = BeanUtil.toBean(menuVO, SysMenu.class);
        if (!this.updateById(menu)) {
            throw new IllegalStateException("修改菜单信息失败!");
        }
        return true;
    }

    @Override
    public boolean delSysMenuById(long id) {
        boolean exists = roleMenuService.exists(QueryWrapper.create().where(SYS_ROLE_MENU.MENU_ID.eq(id)));
        if (exists) {
            throw new IllegalStateException("该菜单被角色关联，不能删除!");
        }
        if (!this.removeById(id)) {
            throw new IllegalStateException("删除菜单信息失败!");
        }
        return true;
    }
}
