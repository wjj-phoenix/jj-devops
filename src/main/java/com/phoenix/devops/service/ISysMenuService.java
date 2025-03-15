package com.phoenix.devops.service;

import com.mybatisflex.core.service.IService;
import com.phoenix.devops.entity.SysMenu;
import com.phoenix.devops.model.vo.SysMenuVO;

import java.util.List;

/**
 * 服务层。
 *
 * @author wjj-phoenix
 * @since 2025-03-11
 */
public interface ISysMenuService extends IService<SysMenu> {

    /**
     * 查询菜单
     * @param condition 条件
     * @return list
     */
    List<SysMenuVO> fetchAllSysMenus(String condition);

    /**
     * 添加菜单
     *
     * @param menuVO 菜单信息
     * @return 主键
     */
    long addSysMenu(SysMenuVO menuVO);

    /**
     * 根据主键修改菜单
     *
     * @param menuVO 菜单信息
     * @return true|false
     */
    boolean modSysMenuById(SysMenuVO menuVO);

    /**
     * 根据主键列表删除菜单
     *
     * @param id 主键
     * @return true|false
     */
    boolean delSysMenuById(long id);
}
