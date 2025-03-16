package com.phoenix.devops.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.mybatisflex.core.paginate.Page;
import com.mybatisflex.core.query.QueryWrapper;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import com.phoenix.devops.common.SelectCommon;
import com.phoenix.devops.entity.SysRole;
import com.phoenix.devops.entity.SysRoleMenu;
import com.phoenix.devops.lang.IPage;
import com.phoenix.devops.mapper.SysRoleMapper;
import com.phoenix.devops.model.vo.SysRoleVO;
import com.phoenix.devops.service.ISysAccountRoleService;
import com.phoenix.devops.service.ISysRoleMenuService;
import com.phoenix.devops.service.ISysRoleService;
import com.phoenix.devops.utils.CollectUtil;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

import static com.phoenix.devops.entity.table.SysAccountRoleTableDef.SYS_ACCOUNT_ROLE;
import static com.phoenix.devops.entity.table.SysRoleMenuTableDef.SYS_ROLE_MENU;

/**
 * 服务层实现。
 *
 * @author wjj-phoenix
 * @since 2025-03-11
 */
@Slf4j
@Service
public class SysRoleServiceImpl extends ServiceImpl<SysRoleMapper, SysRole> implements ISysRoleService {
    @Resource
    private ISysAccountRoleService accountRoleService;
    @Resource
    private ISysRoleMenuService roleMenuService;

    @Override
    public List<SysRoleVO> fetchAllSysRoles() {
        return BeanUtil.copyToList(this.list(), SysRoleVO.class);
    }

    @Override
    public IPage<SysRoleVO> fetchAllSysRolesWithPage(int page, int limit, String condition) {
        Page<SysRole> roles = SelectCommon.findAllPaginateWithRelations(page, limit, condition, mapper);
        if (CollectUtil.isNotEmpty(roles.getRecords())) {
            return IPage.of(
                    roles.getPageNumber(),
                    roles.getPageSize(),
                    roles.getTotalRow(),
                    roles.getTotalPage(),
                    BeanUtil.copyToList(roles.getRecords(), SysRoleVO.class)
            );
        }
        return IPage.empty();
    }

    @Override
    public long addSysRole(SysRoleVO entity) {
        SysRole role = BeanUtil.toBean(entity, SysRole.class);
        if (!this.save(role)) {
            throw new IllegalStateException("添加角色信息失败!");
        }
        if (CollectUtil.isNotEmpty(entity.getMenuIds())) {
            roleMenuService.saveBatch(entity.getMenuIds().stream().map(menuId -> new SysRoleMenu(role.getId(), menuId)).toList());
        }
        return role.getId();
    }

    @Override
    public boolean modSysRoleById(SysRoleVO entity) {
        SysRole role = BeanUtil.toBean(entity, SysRole.class);
        if (!this.updateById(role)) {
            throw new IllegalStateException("修改角色信息失败!");
        }

        if (CollectUtil.isNotEmpty(entity.getMenuIds())) {
            roleMenuService.remove(QueryWrapper.create().where(SYS_ROLE_MENU.ROLE_ID.eq(role.getId())));
            roleMenuService.saveBatch(entity.getMenuIds().stream().map(menuId -> new SysRoleMenu(role.getId(), menuId)).toList());
        }
        return true;
    }

    @Override
    public boolean delSysRoleByIds(Collection<? extends Serializable> ids) {
        boolean exists = accountRoleService.exists(QueryWrapper.create().where(SYS_ACCOUNT_ROLE.ROLE_ID.in(ids)));
        if (exists) {
            throw new IllegalStateException("删除角色中存在已被使用，不能删除!");
        }
        roleMenuService.remove(QueryWrapper.create().where(SYS_ROLE_MENU.ROLE_ID.in(ids)));
        if (!this.removeByIds(ids)) {
            throw new IllegalStateException("删除角色信息失败!");
        }
        return true;
    }
}
