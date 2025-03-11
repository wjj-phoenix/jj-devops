package com.phoenix.devops.function;

import com.phoenix.devops.context.ApplicationContextHolder;
import org.springframework.util.CollectionUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 自定义函数工厂
 */
public class CustomFunctionFactory {

    private static final Map<String, DefaultCustomFunction> customFunctionMap = new HashMap<>();

    public CustomFunctionFactory(List<DefaultCustomFunction> customFunctions) {
        for (DefaultCustomFunction customFunction : customFunctions) {
            customFunctionMap.put(customFunction.functionName(), customFunction);
        }
    }

    /**
     * 从spring容器中获取实现接口的类
     */
    private void register() {
        Map<String, DefaultCustomFunction> beansOfType = ApplicationContextHolder.getInstance().getBeansOfType(DefaultCustomFunction.class);
        if (CollectionUtils.isEmpty(beansOfType)) {
            return;
        }
        beansOfType.values().forEach(iCustomFunction -> {
            customFunctionMap.put(iCustomFunction.functionName(), iCustomFunction);
        });
    }

    /**
     * 通过函数名获取对应自定义函数
     *
     * @param functionName 函数名
     * @return 自定义函数
     */
    public DefaultCustomFunction getFunction(String functionName) {
        return customFunctionMap.get(functionName);
    }

}
