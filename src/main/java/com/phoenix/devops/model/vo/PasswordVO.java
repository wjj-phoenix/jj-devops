package com.phoenix.devops.model.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author wjj-phoenix
 * @since 2025-03-15
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PasswordVO {
    /**
     * 旧密码
     */
    @Schema(description = "当前密码")
    @NotBlank(message = "当前密码不能为空!")
    private String oldPassword;
    /**
     * 新密码
     */
    @Schema(description = "新密码")
    @NotBlank(message = "新密码不能为空!")
    private String newPassword;
    /**
     * 确认密码
     */
    @Schema(description = "确认密码")
    @NotBlank(message = "确认密码不能为空!")
    private String confirmPassword;
}
