package com.phoenix.devops.model.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
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
public class SysRoleVO {
    /**
     * 主键ID
     */
    @Schema(description = "主键ID")
    private Long id;

    /**
     * 角色名称
     */
    @Schema(description = "角色名称")
    private String name;

    /**
     * 角色编码
     */
    @Schema(description = "角色编码")
    private String code;

    /**
     * 角色排序
     */
    @Schema(description = "角色排序")
    private Integer sort;

    /**
     * 角色状态
     */
    @Schema(description = "角色状态")
    private Integer status;

    /**
     * 备注
     */
    @Schema(description = "备注")
    private String remark;

    @Schema(description = "菜单ID集合")
    private List<Long> menuIds;

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

}
