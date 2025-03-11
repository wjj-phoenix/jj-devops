package com.phoenix.devops.function;

import org.springframework.stereotype.Component;

/**
 * 自定义函数的默认实现，增加一层是为了屏蔽底层与上层直接接触
 * @author wjj-phoenix
 * @since 2025-03-10
 */
@Component
public class DefaultFunctionServiceImpl {

    private final CustomFunctionFactory customFunctionFactory;

    public DefaultFunctionServiceImpl(CustomFunctionFactory customFunctionFactory) {
        this.customFunctionFactory = customFunctionFactory;
    }

    public String apply(String functionName, Object value) {
        DefaultCustomFunction function = customFunctionFactory.getFunction(functionName);
        if (function == null) {
            return value.toString();
        }
        return function.apply(value);
    }

    public boolean executeBefore(String functionName) {
        DefaultCustomFunction function = customFunctionFactory.getFunction(functionName);
        return function != null && function.executeBefore();
    }
}
