package com.phoenix.devops.model.vo;

import com.phoenix.devops.utils.TreeUtil;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 实体类。
 *
 * @author wjj-phoenix
 * @since 2025-03-11
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SysMenuVO implements TreeUtil.TreeNode<Long, SysMenuVO> {
    @Schema(description = "主键ID")
    private Long id;

    @Schema(description = "父级id")
    private Long pid;

    @Schema(description = "路由标题")
    private String title;

    @Schema(description = "路由名称")
    private String name;

    @Schema(description = "路径")
    private String path;

    @Schema(description = "图标")
    private String icon;

    @Schema(description = "按钮权限")
    private String code;

    @Schema(description = "组件路径")
    private String component;

    @Schema(description = "菜单类型")
    private Integer type;

    @Schema(description = "排序")
    private Integer sort;

    @Schema(description = "是否隐藏(设置 true 的时候该路由不会在侧边栏出现)")
    private Integer hidden;

    @Schema(description = "是否在面包屑显示(如果设置为 false，则不会在面包屑中显示)")
    private Integer breadcrumb;

    @Schema(description = "设置为 true，它则会固定在 tags-view")
    private Integer affix;

    @Schema(description = "路由的 children 属性中声明的非隐藏子路由只有 1 个且该子路由为叶子节点时，会将这个子路由当做父路由显示在侧边栏")
    private Integer alwaysShow;

    @Schema(description = "设置了该属性进入路由时，则会高亮 activeMenu 属性对应的侧边栏")
    private Integer activeMenu;

    @Schema(description = "是否缓存该路由页面")
    private Integer keepAlive;

    @Schema(description = "外链地址")
    private String redirect;

    @Schema(description = "描述")
    private String description;

    @Schema(description = "创建时间")
    private LocalDateTime createdTime;

    @Schema(description = "更新时间")
    private LocalDateTime updatedTime;

    @Schema(description = "子节点")
    private List<SysMenuVO> children;

    @Override
    public boolean root() {
        return pid == 0;
    }
}
