package com.phoenix.devops.service;

import com.mybatisflex.core.service.IService;
import com.phoenix.devops.entity.SysOperationLog;
import com.phoenix.devops.lang.IPage;

/**
 * 用户操作日志表 服务层。
 *
 * @author wjj-phoenix
 * @since 2025-03-11
 */
public interface ISysOperationLogService extends IService<SysOperationLog> {
    IPage<SysOperationLog> fetchAllOperationLogsByConditionWithPage(Integer page, Integer limit, String condition);

    void addOperationLog(SysOperationLog operationLog);
}
