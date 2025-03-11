package com.phoenix.devops.entity.table;

import com.mybatisflex.core.query.QueryColumn;
import com.mybatisflex.core.table.TableDef;

import java.io.Serial;

/**
 *  表定义层。
 *
 * @author wjj-phoenix
 * @since 2025-03-11
 */
public class SysMenuTableDef extends TableDef {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 
     */
    public static final SysMenuTableDef SYS_MENU = new SysMenuTableDef();

    /**
     * 主键ID
     */
    public final QueryColumn ID = new QueryColumn(this, "id");

    /**
     * 父级id
     */
    public final QueryColumn PID = new QueryColumn(this, "pid");

    /**
     * 按钮权限
     */
    public final QueryColumn CODE = new QueryColumn(this, "code");

    /**
     * 图标
     */
    public final QueryColumn ICON = new QueryColumn(this, "icon");

    /**
     * 路由名称
     */
    public final QueryColumn NAME = new QueryColumn(this, "name");

    /**
     * 路径
     */
    public final QueryColumn PATH = new QueryColumn(this, "path");

    /**
     * 排序
     */
    public final QueryColumn SORT = new QueryColumn(this, "sort");

    /**
     * 菜单类型
     */
    public final QueryColumn TYPE = new QueryColumn(this, "type");

    /**
     * 设置为 true，它则会固定在 tags-view
     */
    public final QueryColumn AFFIX = new QueryColumn(this, "affix");

    /**
     * 路由标题
     */
    public final QueryColumn TITLE = new QueryColumn(this, "title");

    /**
     * 是否隐藏(设置 true 的时候该路由不会在侧边栏出现)
     */
    public final QueryColumn HIDDEN = new QueryColumn(this, "hidden");

    /**
     * 外链地址
     */
    public final QueryColumn REDIRECT = new QueryColumn(this, "redirect");

    /**
     * 组件路径
     */
    public final QueryColumn COMPONENT = new QueryColumn(this, "component");

    /**
     * 是否缓存该路由页面
     */
    public final QueryColumn KEEP_ALIVE = new QueryColumn(this, "keep_alive");

    /**
     * 设置了该属性进入路由时，则会高亮 activeMenu 属性对应的侧边栏
     */
    public final QueryColumn ACTIVE_MENU = new QueryColumn(this, "active_menu");

    /**
     * 路由的 children 属性中声明的非隐藏子路由只有 1 个且该子路由为叶子节点时，会将这个子路由当做父路由显示在侧边栏
     */
    public final QueryColumn ALWAYS_SHOW = new QueryColumn(this, "always_show");

    /**
     * 是否在面包屑显示(如果设置为 false，则不会在面包屑中显示)
     */
    public final QueryColumn BREADCRUMB = new QueryColumn(this, "breadcrumb");

    /**
     * 创建时间
     */
    public final QueryColumn CREATED_TIME = new QueryColumn(this, "created_time");

    /**
     * 描述
     */
    public final QueryColumn DESCRIPTION = new QueryColumn(this, "description");

    /**
     * 更新时间
     */
    public final QueryColumn UPDATED_TIME = new QueryColumn(this, "updated_time");

    /**
     * 所有字段。
     */
    public final QueryColumn ALL_COLUMNS = new QueryColumn(this, "*");

    /**
     * 默认字段，不包含逻辑删除或者 large 等字段。
     */
    public final QueryColumn[] DEFAULT_COLUMNS = new QueryColumn[]{ID, PID, TITLE, NAME, PATH, ICON, CODE, COMPONENT, TYPE, SORT, HIDDEN, BREADCRUMB, AFFIX, ALWAYS_SHOW, ACTIVE_MENU, KEEP_ALIVE, REDIRECT, DESCRIPTION, CREATED_TIME, UPDATED_TIME};

    public SysMenuTableDef() {
        super("", "sys_menu");
    }

    private SysMenuTableDef(String schema, String name, String alisa) {
        super(schema, name, alisa);
    }

    public SysMenuTableDef as(String alias) {
        String key = getNameWithSchema() + "." + alias;
        return getCache(key, k -> new SysMenuTableDef("", "sys_menu", alias));
    }

}
