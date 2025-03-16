package com.phoenix.devops.entity;

import com.mybatisflex.annotation.*;
import lombok.*;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 主机表 实体类。
 *
 * @author wjj-phoenix
 * @since 2025-03-16
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table("machine")
public class Machine implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 主键ID
     */
    @Id(keyType = KeyType.Auto)
    private Long id;

    /**
     * 机器名
     */
    private String name;

    /**
     * 机器IP
     */
    private String ip;

    /**
     * 用于SSH链接的端口
     */
    private Integer port;

    /**
     * 主机操作系统
     */
    private String operatingSystem;

    /**
     * 是否为虚拟机（0：不是；1：是）
     */
    private Integer virtual;

    /**
     * 是否可用（0：不可用；1：可用）
     */
    private Enabled enabled;

    /**
     * 备注信息
     */
    private String remark;

    /**
     * 创建用户
     */
    private Long createdUser;

    /**
     * 创建时间
     */
    @Column(onInsertValue = "now()")
    private LocalDateTime createdTime;

    /**
     * 最近登录时间
     */
    private LocalDateTime latestTime;

    /**
     * 修改时间
     */
    @Column(onInsertValue = "now()", onUpdateValue = "now()")
    private LocalDateTime updatedTime;

    /**
     * 修改用户
     */
    private Long updatedUser;

    @Getter
    @AllArgsConstructor
    public enum Enabled {
        ENABLED(1, "启用"),
        DISABLED(0, "禁用");

        @EnumValue
        private final int code;
        private final String desc;
    }
}
