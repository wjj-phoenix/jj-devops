package com.phoenix.devops.controller;

import com.phoenix.devops.entity.Machine;
import com.phoenix.devops.lang.IPage;
import com.phoenix.devops.model.Add;
import com.phoenix.devops.model.Mod;
import com.phoenix.devops.service.IMachineService;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

/**
 * 主机表 控制层。
 *
 * @author wjj-phoenix
 * @since 2025-03-16
 */
@Valid
@RestController
@RequestMapping("/machine")
public class MachineController {

    @Resource
    private IMachineService service;

    /**
     * 添加主机表。
     *
     * @param machine 主机表
     * @return {@code true} 添加成功，{@code false} 添加失败
     */
    @PostMapping()
    public long addMachine(@Validated({Add.class}) @RequestBody Machine machine) {
        return service.addMachine(machine);
    }

    /**
     * 根据主键删除主机表。
     *
     * @param ids 主键
     * @return {@code true} 删除成功，{@code false} 删除失败
     */
    @DeleteMapping()
    public boolean delMachine(@RequestBody Set<Long> ids) {
        return service.delMachine(ids);
    }

    /**
     * 根据主键更新主机表。
     *
     * @param machine 主机表
     * @return {@code true} 更新成功，{@code false} 更新失败
     */
    @PutMapping()
    public boolean modMachineById(@Validated({Mod.class}) @RequestBody Machine machine) {
        return service.modMachineById(machine);
    }

    /**
     * 根据主机表主键获取详细信息。
     *
     * @param id 主机表主键
     * @return 主机表详情
     */
    @GetMapping("/{id}")
    public Machine fetchMachineById(@PathVariable Long id) {
        return service.fetchMachineById(id);
    }

    /**
     * 分页查询主机表。
     *
     * @param page 分页
     * @return 分页对象
     */
    @GetMapping()
    public IPage<Machine> fetchAllMachinesByConditionWithPage(
            @RequestParam(value = "page", required = false, defaultValue = "1") Integer page,
            @RequestParam(value = "limit", required = false, defaultValue = "20") Integer limit,
            @RequestParam(value = "condition", defaultValue = "", required = false) String condition
    ) {
        return service.fetchAllMachinesByConditionWithPage(page, limit, condition);
    }

}
