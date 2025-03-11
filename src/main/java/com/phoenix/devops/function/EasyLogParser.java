package com.phoenix.devops.function;

import com.phoenix.devops.constants.EasyLogConsts;
import com.phoenix.devops.context.EasyLogCachedExpressionEvaluator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.expression.AnnotatedElementKey;
import org.springframework.expression.EvaluationContext;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author wjj-phoenix
 * @since 2025-03-10
 */
@Slf4j
@Component
public class EasyLogParser implements BeanFactoryAware {

    /**
     * 实现BeanFactoryAware以获取容器中的 beanFactory对象,
     * 拿到beanFactory后便可以获取容器中的bean,用于SpEL表达式的解析
     */
    private BeanFactory beanFactory;

    private static final Pattern PATTERN = Pattern.compile("\\{\\s*(\\w*)\\s*\\{(.*?)}}");

    @Autowired
    private DefaultFunctionServiceImpl customFunctionService;

    private final EasyLogCachedExpressionEvaluator cachedExpressionEvaluator = new EasyLogCachedExpressionEvaluator();

    public Map<String, String> processAfterExec(List<String> expressTemplate, Map<String, String> funcValBeforeExecMap, Method method, Object[] args, Class<?> targetClass, String errMsg, Object result) {
        HashMap<String, String> map = new HashMap<>();
        AnnotatedElementKey elementKey = new AnnotatedElementKey(method, targetClass);
        EvaluationContext evaluationContext = cachedExpressionEvaluator.createEvaluationContext(method, args, beanFactory, errMsg, result);
        for (String template : expressTemplate) {
            if (template.contains("{")) {
                Matcher matcher = PATTERN.matcher(template);
                StringBuilder parsedStr = new StringBuilder();
                //匹配到字符串中的 {*{*}}
                while (matcher.find()) {
                    String paramName = matcher.group(2);
                    Object value = cachedExpressionEvaluator.parseExpression(paramName, elementKey, evaluationContext);
                    String funcName = matcher.group(1);
                    String param = value == null ? "" : value.toString();
                    String functionVal = ObjectUtils.isEmpty(funcName) ? param : getFuncVal(funcValBeforeExecMap, funcName, paramName, param);
                    // 将 functionVal 替换 {*{*}}，然后将 从头截至到 匹配的最后字符 之间的字符串，放入 parsedStr 中
                    matcher.appendReplacement(parsedStr, functionVal);
                }
                // 将 从匹配的最后字符到整个字符串最后 之间的字符串，追加到parsedStr中
                matcher.appendTail(parsedStr);
                map.put(template, parsedStr.toString());
            } else {
                Object value = null;
                try {
                    value = cachedExpressionEvaluator.parseExpression(template, elementKey, evaluationContext);
                } catch (Exception e) {
                    value = template;
                    log.warn(method.getDeclaringClass().getName() + "." + method.getName() + "下 EasyLog 不含表达式或表达式错误: [" + template + "], 如不包含表达式请忽略，如包含请检查是否符合SpEl表达式规范！");
                }
                map.put(template, value == null ? "" : value.toString());
            }
        }
        return map;
    }


    /**
     * 获取执行前的自定义函数值
     *
     * @return map
     */
    public Map<String, String> processBeforeExec(List<String> templates, Method method, Object[] args, Class<?> targetClass) {
        HashMap<String, String> map = new HashMap<>();
        AnnotatedElementKey elementKey = new AnnotatedElementKey(method, targetClass);
        EvaluationContext evaluationContext = cachedExpressionEvaluator.createEvaluationContext(method, args, beanFactory, null, null);
        for (String template : templates) {
            if (!template.contains("{")) {
                continue;
            }
            Matcher matcher = PATTERN.matcher(template);
            while (matcher.find()) {
                String paramName = matcher.group(2);
                if (paramName.contains(EasyLogConsts.POUND_KEY + EasyLogConsts.ERR_MSG) || paramName.contains(EasyLogConsts.POUND_KEY + EasyLogConsts.RESULT)) {
                    continue;
                }
                String funcName = matcher.group(1);
                if (customFunctionService.executeBefore(funcName)) {
                    Object value = cachedExpressionEvaluator.parseExpression(paramName, elementKey, evaluationContext);
                    String apply = customFunctionService.apply(funcName, value == null ? null : value.toString());
                    map.put(getFunctionMapKey(funcName, paramName), apply);
                }
            }
        }
        return map;
    }

    /**
     * 获取前置函数映射的 key
     *
     * @param funcName 方法名
     * @param param 参数
     * @return key
     */
    private String getFunctionMapKey(String funcName, String param) {
        return funcName + param;
    }


    /**
     * 获取自定义函数值
     *
     * @param funcValBeforeExecutionMap 执行之前的函数值
     * @param funcName                  函数名
     * @param paramName                 函数参数名称
     * @param param                     函数参数
     * @return 函数值
     */
    public String getFuncVal(Map<String, String> funcValBeforeExecutionMap, String funcName, String paramName, String param) {
        String val = null;
        if (!CollectionUtils.isEmpty(funcValBeforeExecutionMap)) {
            val = funcValBeforeExecutionMap.get(getFunctionMapKey(funcName, paramName));
        }
        if (ObjectUtils.isEmpty(val)) {
            val = customFunctionService.apply(funcName, param);
        }

        return val;
    }

    @Override
    public void setBeanFactory(@NonNull BeanFactory beanFactory) throws BeansException {
        this.beanFactory = beanFactory;
    }
}
