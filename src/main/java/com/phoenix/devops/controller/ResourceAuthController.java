package com.phoenix.devops.controller;

import com.mybatisflex.core.paginate.Page;
import com.phoenix.devops.entity.ResourceAuth;
import com.phoenix.devops.service.IResourceAuthService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 主机账号 控制层。
 *
 * @author wjj-phoenix
 * @since 2025-03-16
 */
@RestController
@RequestMapping("/resourceAuth")
public class ResourceAuthController {

    @Resource
    private IResourceAuthService iResourceAuthService;

    /**
     * 添加主机账号。
     *
     * @param resourceAuth 主机账号
     * @return {@code true} 添加成功，{@code false} 添加失败
     */
    @PostMapping("save")
    public boolean save(@RequestBody ResourceAuth resourceAuth) {
        return iResourceAuthService.save(resourceAuth);
    }

    /**
     * 根据主键删除主机账号。
     *
     * @param id 主键
     * @return {@code true} 删除成功，{@code false} 删除失败
     */
    @DeleteMapping("remove/{id}")
    public boolean remove(@PathVariable Long id) {
        return iResourceAuthService.removeById(id);
    }

    /**
     * 根据主键更新主机账号。
     *
     * @param resourceAuth 主机账号
     * @return {@code true} 更新成功，{@code false} 更新失败
     */
    @PutMapping("update")
    public boolean update(@RequestBody ResourceAuth resourceAuth) {
        return iResourceAuthService.updateById(resourceAuth);
    }

    /**
     * 查询所有主机账号。
     *
     * @return 所有数据
     */
    @GetMapping("list")
    public List<ResourceAuth> list() {
        return iResourceAuthService.list();
    }

    /**
     * 根据主机账号主键获取详细信息。
     *
     * @param id 主机账号主键
     * @return 主机账号详情
     */
    @GetMapping("getInfo/{id}")
    public ResourceAuth getInfo(@PathVariable Long id) {
        return iResourceAuthService.getById(id);
    }

    /**
     * 分页查询主机账号。
     *
     * @param page 分页对象
     * @return 分页对象
     */
    @GetMapping("page")
    public Page<ResourceAuth> page(Page<ResourceAuth> page) {
        return iResourceAuthService.page(page);
    }

}
