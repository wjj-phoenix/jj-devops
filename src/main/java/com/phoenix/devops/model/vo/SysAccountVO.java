package com.phoenix.devops.model.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

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
public class SysAccountVO {
    /**
     * 主键ID
     */
    @Schema(description = "主键ID")
    private Long id;

    /**
     * 用户名
     */
    @Schema(description = "用户名")
    private String username;

    /**
     * 密码
     */
    @Schema(description = "密码")
    private String password;

    /**
     * 头像
     */
    @Schema(description = "头像")
    private String avatar;

    /**
     * 邮箱
     */
    @Schema(description = "邮箱")
    private String email;

    /**
     * 真实姓名
     */
    @Schema(description = "真实姓名")
    private String realName;

    /**
     * 备注
     */
    @Schema(description = "备注")
    private String remark;

    /**
     * 用户是否被锁
     */
    @Schema(description = "用户是否被锁")
    private Integer locked;

    /**
     * 用户是否可用
     */
    @Schema(description = "用户是否可用")
    private Integer enabled;

    /**
     * 创建时间
     */
    @Schema(description = "创建时间")
    private LocalDateTime createdTime;

    /**
     * 更新时间
     */
    @Schema(description = "更新时间")
    private LocalDateTime updatedTime;

    /**
     * 最新登录时间
     */
    @Schema(description = "最新登录时间")
    private LocalDateTime latestLoginTime;

    /**
     * 创建用户
     */
    @Schema(description = "创建用户")
    private Long createdUser;

    @Schema(description = "角色集合")
    private List<String> roles;

    @Schema(description = "角色ID集合")
    private Set<Long> roleIds;
}
