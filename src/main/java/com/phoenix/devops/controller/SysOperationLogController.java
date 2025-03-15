package com.phoenix.devops.controller;

import com.phoenix.devops.entity.SysOperationLog;
import com.phoenix.devops.lang.IPage;
import com.phoenix.devops.service.ISysOperationLogService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 用户操作日志表 控制层。
 *
 * @author wjj-phoenix
 * @since 2025-03-11
 */
@RestController
@RequestMapping("/operation-log")
public class SysOperationLogController {

    @Resource
    private ISysOperationLogService service;

    /**
     * 分页查询。
     *
     * @param page      页码 分页对象
     * @param limit     每页大小
     * @param condition 条件
     * @return 分页对象
     */
    @GetMapping()
    @Operation(summary = "分页查询日志信息",
            description = "根据条件【条件可有可无】分页查询日志信息",
            parameters = {
                    @Parameter(name = "page", description = "页码"),
                    @Parameter(name = "limit", description = "每页大小"),
                    @Parameter(name = "condition", description = "条件")
            }
    )
    public IPage<SysOperationLog> fetchAllOperationLogsByConditionWithPage(
            @RequestParam(value = "page", required = false, defaultValue = "1") Integer page,
            @RequestParam(value = "limit", required = false, defaultValue = "20") Integer limit,
            @RequestParam(value = "condition", defaultValue = "", required = false) String condition
    ) {
        return service.fetchAllOperationLogsByConditionWithPage(page, limit, condition);
    }

}
