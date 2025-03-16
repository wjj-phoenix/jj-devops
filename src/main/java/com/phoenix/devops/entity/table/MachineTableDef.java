package com.phoenix.devops.entity.table;

import com.mybatisflex.core.query.QueryColumn;
import com.mybatisflex.core.table.TableDef;

import java.io.Serial;

/**
 * 主机表 表定义层。
 *
 * @author wjj-phoenix
 * @since 2025-03-16
 */
public class MachineTableDef extends TableDef {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 主机表
     */
    public static final MachineTableDef MACHINE = new MachineTableDef();

    /**
     * 主键ID
     */
    public final QueryColumn ID = new QueryColumn(this, "id");

    /**
     * 机器IP
     */
    public final QueryColumn IP = new QueryColumn(this, "ip");

    /**
     * 机器名
     */
    public final QueryColumn NAME = new QueryColumn(this, "name");

    /**
     * 用于SSH链接的端口
     */
    public final QueryColumn PORT = new QueryColumn(this, "port");

    /**
     * 备注信息
     */
    public final QueryColumn REMARK = new QueryColumn(this, "remark");

    /**
     * 是否可用（0：不可用；1：可用）
     */
    public final QueryColumn ENABLED = new QueryColumn(this, "enabled");

    /**
     * 是否为虚拟机（0：不是；1：是）
     */
    public final QueryColumn VIRTUAL = new QueryColumn(this, "virtual");

    /**
     * 最近登录时间
     */
    public final QueryColumn LATEST_TIME = new QueryColumn(this, "latest_time");

    /**
     * 创建时间
     */
    public final QueryColumn CREATED_TIME = new QueryColumn(this, "created_time");

    /**
     * 创建用户
     */
    public final QueryColumn CREATED_USER = new QueryColumn(this, "created_user");

    /**
     * 修改时间
     */
    public final QueryColumn UPDATED_TIME = new QueryColumn(this, "updated_time");

    /**
     * 修改用户
     */
    public final QueryColumn UPDATED_USER = new QueryColumn(this, "updated_user");

    /**
     * 主机操作系统
     */
    public final QueryColumn OPERATING_SYSTEM = new QueryColumn(this, "operating_system");

    /**
     * 所有字段。
     */
    public final QueryColumn ALL_COLUMNS = new QueryColumn(this, "*");

    /**
     * 默认字段，不包含逻辑删除或者 large 等字段。
     */
    public final QueryColumn[] DEFAULT_COLUMNS = new QueryColumn[]{ID, NAME, IP, PORT, OPERATING_SYSTEM, VIRTUAL, ENABLED, REMARK, CREATED_USER, CREATED_TIME, LATEST_TIME, UPDATED_TIME, UPDATED_USER};

    public MachineTableDef() {
        super("", "machine");
    }

    private MachineTableDef(String schema, String name, String alisa) {
        super(schema, name, alisa);
    }

    public MachineTableDef as(String alias) {
        String key = getNameWithSchema() + "." + alias;
        return getCache(key, k -> new MachineTableDef("", "machine", alias));
    }

}
