package com.phoenix.devops.lang;

import java.util.LinkedList;
import java.util.List;

/**
 * @author wjj-phoenix
 * @since 2025-03-16
 */
public final class Constant {
    public static final Integer OFFSET_HOUR = 6;
    public static final String HEADER = "Authorization";
    public static final String JWT_AUTH_PREFIX = "Bearer ";
    public static final String BASIC_AUTH_PREFIX = "Basic ";
    public static final byte[] KEY = "1234567890".getBytes();

    public static final String PASSWORD = "123456";

    public static final List<String> WHITES = new LinkedList<>();

    static {
        // 注意，后面的url需要写：
        //  1. 整个请求映射地址
        //  2. 可接受**通配符
        // WHITES.add(Pair.of(HttpMethod.GET, "/**"));
        // WHITES.add(Pair.of(HttpMethod.POST, "/**"));
        // WHITES.add(Pair.of(HttpMethod.PUT, "/**"));
        WHITES.add("/login");
        WHITES.add("/logout");
        WHITES.add("/verify");
        WHITES.add("/favicon.ico");
        WHITES.add("/swagger-ui.html");
        WHITES.add("/swagger-ui/**");
        WHITES.add("/v3/**");
    }
}
