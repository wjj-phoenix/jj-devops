package com.phoenix.devops.entity;

import com.mybatisflex.annotation.*;
import lombok.*;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

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
@Table("sys_account")
public class SysAccount implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 主键ID
     */
    @Id(keyType = KeyType.Auto)
    private Long id;

    /**
     * 用户名
     */
    private String username;

    /**
     * 密码
     */
    private String password;

    /**
     * 头像
     */
    private String avatar;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 真实姓名
     */
    private String realName;

    /**
     * 备注
     */
    private String remark;

    /**
     * 用户是否被锁
     */
    private Locked locked;

    /**
     * 用户是否可用
     */
    private Enabled enabled;

    /**
     * 创建时间
     */
    private LocalDateTime createdTime;

    /**
     * 更新时间
     */
    private LocalDateTime updatedTime;

    /**
     * 最新登录时间
     */
    private LocalDateTime latestLoginTime;

    /**
     * 创建用户
     */
    private Long createdUser;

    @RelationManyToMany(
            selfField = "id",
            joinTable = "sys_account_role", joinSelfColumn = "account_id", joinTargetColumn = "role_id",
            targetTable = "sys_role", targetField = "id"
    )
    private List<SysRole> roles;

    @Column(ignore = true)
    private Collection<String> authorities;

    @Getter
    @AllArgsConstructor
    public enum Enabled {
        ENABLED(1, "启用"),
        DISABLED(0, "禁用");

        @EnumValue
        private final int code;
        private final String desc;
    }

    @Getter
    @AllArgsConstructor
    public enum Locked {
        LOCKED(1, "锁定"),
        UNLOCKED(0, "未锁定");

        @EnumValue
        private final int code;
        private final String desc;
    }
}
