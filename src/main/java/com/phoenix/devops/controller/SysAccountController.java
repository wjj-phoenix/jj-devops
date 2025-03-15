package com.phoenix.devops.controller;

import com.phoenix.devops.entity.SysAccount;
import com.phoenix.devops.lang.IPage;
import com.phoenix.devops.model.Add;
import com.phoenix.devops.model.vo.SysAccountVO;
import com.phoenix.devops.service.ISysAccountService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import jakarta.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

/**
 * 控制层。
 *
 * @author wjj-phoenix
 * @since 2025-03-11
 */
@RestController
@RequestMapping("/account")
public class SysAccountController {

    @Resource
    private ISysAccountService service;

    /**
     * 添加。
     *
     * @param accountVO
     * @return {@code true} 添加成功，{@code false} 添加失败
     */
    @PostMapping("save")
    public long save(@Validated({Add.class}) @RequestBody SysAccountVO accountVO) {
        return service.addSysAccount(accountVO);
    }

    /**
     * 根据主键删除。
     *
     * @param ids 主键
     * @return {@code true} 删除成功，{@code false} 删除失败
     */
    @DeleteMapping()
    public boolean remove(@RequestBody Set<Long> ids) {
        return service.delSysAccount(ids);
    }

    /**
     * 根据主键更新。
     *
     * @param accountVO
     * @return {@code true} 更新成功，{@code false} 更新失败
     */
    @PutMapping()
    public boolean modSysAccountById(@RequestBody SysAccountVO accountVO) {
        return service.modSysAccountById(accountVO);
    }

    /**
     * 根据主键获取详细信息。
     *
     * @param username 主键
     * @return 详情
     */
    @GetMapping("info")
    public SysAccount getInfo(@RequestParam String username) {
        return service.fetchSysAccountWithRelationsByUsername(username);
    }

    /**
     * 分页查询。
     *
     * @param page      页码 分页对象
     * @param limit     每页大小
     * @param condition 条件
     * @return 分页对象
     */
    @GetMapping()
    @Operation(summary = "分页查询用户信息",
            description = "根据条件【条件可有可无】分页查询用户信息",
            parameters = {
                    @Parameter(name = "page", description = "页码"),
                    @Parameter(name = "limit", description = "每页大小"),
                    @Parameter(name = "condition", description = "条件")
            }
    )
    public IPage<SysAccountVO> fetchAllAccountsByCondition(
            @RequestParam(value = "page", required = false, defaultValue = "1") Integer page,
            @RequestParam(value = "limit", required = false, defaultValue = "20") Integer limit,
            @RequestParam(value = "condition", defaultValue = "", required = false) String condition
    ) {
        return service.fetchAllAccountsByCondition(page, limit, condition);
    }

}
