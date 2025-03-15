package com.phoenix.devops.controller;

import com.phoenix.devops.model.Add;
import com.phoenix.devops.model.vo.SysMenuVO;
import com.phoenix.devops.service.ISysMenuService;
import jakarta.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 控制层。
 *
 * @author wjj-phoenix
 * @since 2025-03-11
 */
@RestController
@RequestMapping("/menu")
public class SysMenuController {

    @Resource
    private ISysMenuService service;

    /**
     * 添加。
     *
     * @param menuVO 菜单信息
     * @return {@code true} 添加成功，{@code false} 添加失败
     */
    @PostMapping()
    public long addSysMenu(@Validated({Add.class}) @RequestBody SysMenuVO menuVO) {
        return service.addSysMenu(menuVO);
    }

    /**
     * 根据主键删除。
     *
     * @param id 主键
     * @return {@code true} 删除成功，{@code false} 删除失败
     */
    @DeleteMapping("/{id}")
    public boolean remove(@PathVariable Long id) {
        return service.delSysMenuById(id);
    }

    /**
     * 根据主键更新。
     *
     * @param menuVO 菜单信息
     * @return {@code true} 更新成功，{@code false} 更新失败
     */
    @PutMapping()
    public boolean modSysMenuById(@RequestBody SysMenuVO menuVO) {
        return service.modSysMenuById(menuVO);
    }

    /**
     * 查询所有。
     *
     * @return 所有数据
     */
    @GetMapping()
    public List<SysMenuVO> fetchAllSysMenus(@RequestParam String condition) {
        return service.fetchAllSysMenus(condition);
    }
}
