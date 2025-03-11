package com.phoenix.devops.entity;

import com.mybatisflex.annotation.Id;
import com.mybatisflex.annotation.KeyType;
import com.mybatisflex.annotation.Table;
import java.io.Serializable;
import java.time.LocalDateTime;

import java.io.Serial;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *  实体类。
 *
 * @author wjj-phoenix
 * @since 2025-03-11
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table("sys_menu")
public class SysMenu implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 主键ID
     */
    @Id(keyType = KeyType.Auto)
    private Long id;

    /**
     * 父级id
     */
    private Long pid;

    /**
     * 路由标题
     */
    private String title;

    /**
     * 路由名称
     */
    private String name;

    /**
     * 路径
     */
    private String path;

    /**
     * 图标
     */
    private String icon;

    /**
     * 按钮权限
     */
    private String code;

    /**
     * 组件路径
     */
    private String component;

    /**
     * 菜单类型
     */
    private Integer type;

    /**
     * 排序
     */
    private Integer sort;

    /**
     * 是否隐藏(设置 true 的时候该路由不会在侧边栏出现)
     */
    private Integer hidden;

    /**
     * 是否在面包屑显示(如果设置为 false，则不会在面包屑中显示)
     */
    private Integer breadcrumb;

    /**
     * 设置为 true，它则会固定在 tags-view
     */
    private Integer affix;

    /**
     * 路由的 children 属性中声明的非隐藏子路由只有 1 个且该子路由为叶子节点时，会将这个子路由当做父路由显示在侧边栏
     */
    private Integer alwaysShow;

    /**
     * 设置了该属性进入路由时，则会高亮 activeMenu 属性对应的侧边栏
     */
    private Integer activeMenu;

    /**
     * 是否缓存该路由页面
     */
    private Integer keepAlive;

    /**
     * 外链地址
     */
    private String redirect;

    /**
     * 描述
     */
    private String description;

    /**
     * 创建时间
     */
    private LocalDateTime createdTime;

    /**
     * 更新时间
     */
    private LocalDateTime updatedTime;

}
