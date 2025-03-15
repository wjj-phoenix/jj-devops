package com.phoenix.devops.controller;

import com.phoenix.devops.lang.IPage;
import com.phoenix.devops.model.Add;
import com.phoenix.devops.model.Mod;
import com.phoenix.devops.model.vo.SysRoleVO;
import com.phoenix.devops.service.ISysRoleService;
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
@RequestMapping("/role")
public class SysRoleController {
    @Resource
    private ISysRoleService service;

    /**
     * 添加。
     *
     * @param roleVO 角色信息
     * @return {@code true} 添加成功，{@code false} 添加失败
     */
    @PostMapping()
    public long addSysRole(@Validated({Add.class}) @RequestBody SysRoleVO roleVO) {
        return service.addSysRole(roleVO);
    }

    /**
     * 根据主键删除。
     *
     * @param ids 主键
     * @return {@code true} 删除成功，{@code false} 删除失败
     */
    @DeleteMapping()
    public boolean delSysRoleByIds(@RequestBody Set<Long> ids) {
        return service.delSysRoleByIds(ids);
    }

    /**
     * 根据主键更新。
     *
     * @param roleVO
     * @return {@code true} 更新成功，{@code false} 更新失败
     */
    @PutMapping()
    public boolean modSysRoleById(@Validated({Mod.class}) @RequestBody SysRoleVO roleVO) {
        return service.modSysRoleById(roleVO);
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
    public IPage<SysRoleVO> fetchAllSysRolesWithPage(
            @RequestParam(value = "page", required = false, defaultValue = "1") Integer page,
            @RequestParam(value = "limit", required = false, defaultValue = "20") Integer limit,
            @RequestParam(value = "condition", defaultValue = "", required = false) String condition
    ) {
        return service.fetchAllSysRolesWithPage(page, limit, condition);
    }

}
