package com.phoenix.devops.service;

import com.mybatisflex.core.service.IService;
import com.phoenix.devops.entity.SysRole;
import com.phoenix.devops.lang.IPage;
import com.phoenix.devops.model.vo.SysRoleVO;

import java.io.Serializable;
import java.util.Collection;

/**
 * 服务层。
 *
 * @author wjj-phoenix
 * @since 2025-03-11
 */
public interface ISysRoleService extends IService<SysRole> {
    /**
     * 分页查询所有数据
     * @param page 页码
     * @param limit 每页数据量
     * @param condition 条件
     * @return 分页数据
     */
    IPage<SysRoleVO> fetchAllSysRolesWithPage(int page, int limit, String condition);

    /**
     * 新增数据
     * @param entity 实体对象
     * @return 新增结果
     */
    long addSysRole(SysRoleVO entity);

    /**
     * 修改数据
     * @param entity 实体对象
     * @return 修改结果
     */
    boolean modSysRoleById(SysRoleVO entity);

    /**
     * 删除数据
     * @param ids 主键列表
     * @return 删除结果
     */
    boolean delSysRoleByIds(Collection<? extends Serializable> ids);
}
