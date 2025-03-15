package com.phoenix.devops.service.impl;

import com.mybatisflex.core.paginate.Page;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import com.phoenix.devops.common.SelectCommon;
import com.phoenix.devops.entity.SysOperationLog;
import com.phoenix.devops.lang.IPage;
import com.phoenix.devops.mapper.SysOperationLogMapper;
import com.phoenix.devops.service.ISysOperationLogService;
import com.phoenix.devops.utils.CollectUtil;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.stereotype.Service;

/**
 * 用户操作日志表 服务层实现。
 *
 * @author wjj-phoenix
 * @since 2025-03-11
 */
@Service
@CacheConfig(cacheNames = "sysOperationLog")
public class SysOperationLogServiceImpl extends ServiceImpl<SysOperationLogMapper, SysOperationLog> implements ISysOperationLogService {
    @Override
    public IPage<SysOperationLog> fetchAllOperationLogsByConditionWithPage(Integer page, Integer limit, String condition) {
        Page<SysOperationLog> allPaginate = SelectCommon.findAllPaginate(page, limit, condition, mapper);
        if (CollectUtil.isNotEmpty(allPaginate.getRecords()))
            return IPage.of(allPaginate.getPageNumber(), allPaginate.getPageSize(), allPaginate.getTotalRow(), allPaginate.getTotalPage(), allPaginate.getRecords());
        return IPage.empty();
    }

    @Override
    public void addOperationLog(SysOperationLog operationLog) {
        if (!this.save(operationLog)) {
            throw new IllegalStateException("添加用户操作日志失败");
        }
    }
}
