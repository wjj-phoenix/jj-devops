package com.phoenix.devops.service;

import com.phoenix.devops.model.vo.LoginInfo;
import com.phoenix.devops.model.vo.LoginRespVO;
import com.phoenix.devops.model.vo.SysAccountVO;

/**
 * @author wjj-phoenix
 * @since 2025-03-16
 */
public interface ISysAuthenticationService {
    /**
     * 用户登录请求
     *
     * @param info 登录输入信息
     * @return 登录成功的响应
     */
    LoginRespVO login(LoginInfo info);


    /**
     * 根据认证信息查询用户信息
     *
     * @return 账户信息
     */
    SysAccountVO info();
}
