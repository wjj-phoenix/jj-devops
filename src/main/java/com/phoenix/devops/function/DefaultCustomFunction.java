package com.phoenix.devops.function;

import org.springframework.stereotype.Component;

/**
 * @author WangQingFei(wjj-phoenix)
 * @since 2025-03-10
 */
@Component
public class DefaultCustomFunction {
    public boolean executeBefore() {
        return false;
    }


    public String functionName() {
        return "defaultName";
    }


    public String apply(Object value) {
        return null;
    }
}
