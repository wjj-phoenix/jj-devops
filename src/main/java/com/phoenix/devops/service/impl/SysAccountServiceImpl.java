package com.phoenix.devops.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.mybatisflex.core.paginate.Page;
import com.mybatisflex.core.query.QueryWrapper;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import com.phoenix.devops.common.SelectCommon;
import com.phoenix.devops.entity.SysAccount;
import com.phoenix.devops.entity.SysAccountRole;
import com.phoenix.devops.lang.IPage;
import com.phoenix.devops.mapper.SysAccountMapper;
import com.phoenix.devops.model.vo.PasswordVO;
import com.phoenix.devops.model.vo.SysAccountVO;
import com.phoenix.devops.service.ISysAccountRoleService;
import com.phoenix.devops.service.ISysAccountService;
import com.phoenix.devops.service.ISysRoleService;
import com.phoenix.devops.utils.CollectUtil;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.List;
import java.util.Set;

import static com.phoenix.devops.entity.table.SysAccountRoleTableDef.SYS_ACCOUNT_ROLE;
import static com.phoenix.devops.entity.table.SysAccountTableDef.SYS_ACCOUNT;

/**
 * 服务层实现。
 *
 * @author wjj-phoenix
 * @since 2025-03-11
 */
@Slf4j
@Service
public class SysAccountServiceImpl extends ServiceImpl<SysAccountMapper, SysAccount> implements ISysAccountService {
    @Resource
    private ISysAccountRoleService accountRoleService;
    @Resource
    private ISysRoleService roleService;

    @Override
    public SysAccount fetchSysAccountWithRelationsByUsername(String username) {
        return mapper.selectOneWithRelationsByQuery(QueryWrapper.create().where(SYS_ACCOUNT.USERNAME.eq(username)));
    }

    @Override
    public SysAccount fetchAccountByUsername(String username) {
        return this.getOne(QueryWrapper.create().where(SYS_ACCOUNT.USERNAME.eq(username)));
    }

    @Override
    public IPage<SysAccountVO> fetchAllAccountsByCondition(Integer page, Integer limit, String condition) {
        Page<SysAccount> accountPage = SelectCommon.findAllPaginate(page, limit, condition, mapper);
        if (CollUtil.isNotEmpty(accountPage.getRecords())) {
            List<SysAccountVO> accountVOS = BeanUtil.copyToList(accountPage.getRecords(), SysAccountVO.class);
            return IPage.of(accountPage.getPageNumber(), accountPage.getPageSize(), accountPage.getTotalPage(), accountPage.getTotalRow(), accountVOS);
        }
        return IPage.empty();
    }

    @Override
    public long addSysAccount(SysAccountVO accountVO) {
        SysAccount account = BeanUtil.toBean(accountVO, SysAccount.class);
        if (!this.save(account)) {
            throw new IllegalStateException("添加用户信息失败");
        }
        if (CollectUtil.isNotEmpty(accountVO.getRoleIds())) {
            accountRoleService.saveBatch(accountVO.getRoleIds().stream().map(roleId -> SysAccountRole.builder().accountId(account.getId()).roleId(roleId).build()).toList());
        }
        return account.getId();
    }

    @Override
    public boolean modSysAccountById(SysAccountVO accountVO) {
        SysAccount account = BeanUtil.toBean(accountVO, SysAccount.class);
        if (!this.updateById(account)) {
            throw new IllegalStateException("修改用户信息失败");
        }
        if (CollectUtil.isNotEmpty(accountVO.getRoleIds())) {
            accountRoleService.remove(QueryWrapper.create().where(SYS_ACCOUNT_ROLE.ACCOUNT_ID.eq(account.getId())));
            accountRoleService.saveBatch(accountVO.getRoleIds().stream().map(roleId -> SysAccountRole.builder().accountId(account.getId()).roleId(roleId).build()).toList());
        }
        return true;
    }

    @Override
    public boolean delSysAccount(Set<Long> ids) {
        if (!this.removeByIds(ids)) {
            throw new IllegalStateException("删除用户失败");
        }
        accountRoleService.remove(QueryWrapper.create().where(SYS_ACCOUNT_ROLE.ACCOUNT_ID.in(ids)));
        return true;
    }

    @Override
    public boolean modSysAccountPassword(Long id, PasswordVO passwordVO) {
        SysAccount account = this.getById(id);
        Assert.notNull(account, "用户不存在");

        if (StrUtil.equals(passwordVO.getNewPassword(), passwordVO.getOldPassword())) {
            throw new IllegalArgumentException("新密码与旧密码一致");
        }
        if (!StrUtil.equals(passwordVO.getNewPassword(), passwordVO.getConfirmPassword())) {
            throw new IllegalArgumentException("新密码与确认密码不一致");
        }
        if (!this.updateById(account)) {
            throw new IllegalStateException("修改密码失败");
        }
        return true;
    }
}
