package com.phoenix.devops.service;

import com.mybatisflex.core.service.IService;
import com.phoenix.devops.entity.Machine;
import com.phoenix.devops.lang.IPage;

import java.util.Set;

/**
 * 主机表 服务层。
 *
 * @author wjj-phoenix
 * @since 2025-03-16
 */
public interface IMachineService extends IService<Machine> {
    /**
     * 根据主键查询主机表
     * @param id 主键
     * @return 主机表
     */
    Machine fetchMachineById(Long id);
    /**
     * 分页查询主机表
     * @param page 页码
     * @param limit 每页数量
     * @param condition 条件
     * @return page
     */
    IPage<Machine> fetchAllMachinesByConditionWithPage(Integer page, Integer limit, String condition);

    /**
     * 添加主机表
     * @param machine 主机表
     * @return 主键
     */
    long addMachine(Machine machine);

    /**
     * 修改主机表
     * @param machine 主机表
     * @return 是否成功
     */
    boolean modMachineById(Machine machine);

    /**
     * 删除主机表
     * @param ids 主键集合
     * @return 是否成功
     */
    boolean delMachine(Set<Long> ids);
}
