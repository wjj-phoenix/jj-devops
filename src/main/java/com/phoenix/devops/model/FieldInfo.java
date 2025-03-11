package com.phoenix.devops.model;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * @author wjj-phoenix
 * @since 2025-03-10
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FieldInfo {
    private String createName;

    /**
     * 变更时间
     */
    private LocalDateTime changeTime;

    /**
     * 变更字段
     */
    private String changeField;

    /**
     * 旧数据值
     */
    private String beforeChange;

    /**
     * 新数据值
     */

    private String afterChange;
    /**
     * ID类型
     */
    private Long typeId;

    /**
     * 备注
     */
    private String remark;
}
