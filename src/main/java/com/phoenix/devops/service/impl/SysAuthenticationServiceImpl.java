package com.phoenix.devops.service.impl;

import cn.dev33.satoken.SaManager;
import cn.dev33.satoken.stp.SaLoginModel;
import cn.dev33.satoken.stp.StpUtil;
import com.phoenix.devops.entity.SysAccount;
import com.phoenix.devops.model.vo.LoginInfo;
import com.phoenix.devops.model.vo.LoginRespVO;
import com.phoenix.devops.model.vo.SysAccountVO;
import com.phoenix.devops.service.ISysAccountService;
import com.phoenix.devops.service.ISysAuthenticationService;
import com.phoenix.devops.service.ISysMenuService;
import com.phoenix.devops.service.ISysRoleService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.time.LocalDateTime;

/**
 * @author wjj-phoenix
 * @since 2025-03-16
 */
@Slf4j
@Service
public class SysAuthenticationServiceImpl implements ISysAuthenticationService {
    @Resource
    private ISysAccountService accountService;
    @Resource
    private ISysRoleService roleService;
    @Resource
    private ISysMenuService menuService;


    @Override
    public LoginRespVO login(LoginInfo info) {
        SysAccount account = accountService.fetchAccountByUsername(info.getUsername());
        Assert.notNull(account, String.format("指定用户【%s】不存在", info.getUsername()));

        String password = info.getPassword();

        SaLoginModel saLoginModel = new SaLoginModel()
                .setDevice("PC") // 此次登录的客户端设备类型
                .setIsLastingCookie(false) // 是否为持久Cookie（临时Cookie在浏览器关闭时会自动删除，持久Cookie在重新打开后依然存在）
                .setTimeout(60 * 60)    // 指定此次登录token的有效期, 单位:秒 （如未指定，自动取全局配置的 timeout 值）
                .setExtra("name", account.getRealName())    // Token挂载的扩展参数 （此方法只有在集成jwt插件时才会生效）
                .setIsWriteHeader(false)         // 是否在登录后将 Token 写入到响应头
                ;
        StpUtil.login(account.getId(), saLoginModel);
        log.info("user 【{}】 logged into the system at the {}.", info.getUsername(), LocalDateTime.now());
        String token = StpUtil.getTokenValue();
        String tokenName = SaManager.getConfig().getTokenName();
        return LoginRespVO.builder()
                .userId(account.getId())
                .accessToken(token)
                .refreshToken(tokenName)
                .expiresTime(LocalDateTime.now())
                .build();
    }

    @Override
    public SysAccountVO info() {
        if (StpUtil.isLogin()) {
            long userId = StpUtil.getLoginIdAsLong();
            return accountService.fetchSysAccountWithRelationsByID(userId);
        }
        return null;
    }
}
