package com.phoenix.devops.service;

import com.mybatisflex.core.service.IService;
import com.phoenix.devops.entity.SysAccount;
import com.phoenix.devops.lang.IPage;
import com.phoenix.devops.model.vo.PasswordVO;
import com.phoenix.devops.model.vo.SysAccountVO;

import java.util.Set;

/**
 * 服务层。
 *
 * @author wjj-phoenix
 * @since 2025-03-11
 */
public interface ISysAccountService extends IService<SysAccount> {
    /**
     * 根据用户名查询关联信息
     *
     * @param username 用户名
     * @return 用户信息
     */
    SysAccount fetchSysAccountWithRelationsByUsername(String username);

    /**
     * 更具用户名查询用户信息
     *
     * @param username 用户名
     * @return 用户信息
     */
    SysAccount fetchAccountByUsername(String username);

    /**
     * 根据条件分页查询用户信息
     *
     * @param page      页码
     * @param limit     每页大小
     * @param condition 条件
     * @return 分页数据
     */
    IPage<SysAccountVO> fetchAllAccountsByCondition(Integer page, Integer limit, String condition);

    /**
     * 添加用户信息
     *
     * @param accountVO 账户信息
     * @return 账户主键ID
     */
    long addSysAccount(SysAccountVO accountVO);

    /**
     * 修改用户信息
     *
     * @param accountVO 账户信息
     * @return true|false
     */
    boolean modSysAccountById(SysAccountVO accountVO);

    /**
     * 删除用户信息
     *
     * @param ids 账户主键ID
     * @return true|false
     */
    boolean delSysAccount(Set<Long> ids);

    /**
     * 删除用户信息
     *
     * @param id 账户主键ID
     * @return true|false
     */
    boolean modSysAccountPassword(Long id, PasswordVO passwordVO);
}
