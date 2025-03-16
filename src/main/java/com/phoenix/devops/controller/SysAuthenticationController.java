package com.phoenix.devops.controller;

import com.phoenix.devops.model.vo.LoginInfo;
import com.phoenix.devops.model.vo.LoginRespVO;
import com.phoenix.devops.model.vo.SysAccountVO;
import com.phoenix.devops.service.ISysAuthenticationService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author wjj-phoenix
 * @since 2025-03-16
 */
@Valid
@RestController
public class SysAuthenticationController {
    @Resource
    private ISysAuthenticationService service;

    @PostMapping("/login")
    @Operation(summary = "登录接口")
    public LoginRespVO login(@Validated @RequestBody LoginInfo info) {
        return service.login(info);
    }

    @GetMapping("/info")
    @Operation(summary = "获取个人信息")
    public SysAccountVO info() {
        return service.info();
    }
}
