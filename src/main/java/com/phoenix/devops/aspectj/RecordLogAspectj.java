package com.phoenix.devops.aspectj;

import cn.hutool.core.bean.BeanUtil;
import com.mybatisflex.core.service.IService;
import com.phoenix.devops.annotation.RecordLog;
import com.phoenix.devops.function.DefaultOperatorServiceImpl;
import com.phoenix.devops.function.EasyLogParser;
import com.phoenix.devops.model.FieldInfo;
import com.phoenix.devops.model.MethodExecuteResult;
import com.phoenix.devops.model.RecordLogInfo;
import com.phoenix.devops.service.impl.DefaultLogRecordServiceImpl;
import com.phoenix.devops.util.CollectUtil;
import com.phoenix.devops.util.JsonUtil;
import com.phoenix.devops.util.ReflectionUtil;
import com.phoenix.devops.util.StringUtil;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.aop.support.AopUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.springframework.util.ObjectUtils;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author wjj-phoenix
 * @since 2025-03-10
 */
@Slf4j
@Aspect
@Component
public class RecordLogAspectj {
    private final DefaultLogRecordServiceImpl logRecordService;

    private final DefaultOperatorServiceImpl operatorService;

    private final EasyLogParser easyLogParser;

    public RecordLogAspectj(DefaultLogRecordServiceImpl logRecordService, DefaultOperatorServiceImpl operatorService, EasyLogParser easyLogParser) {
        this.logRecordService = logRecordService;
        this.operatorService = operatorService;
        this.easyLogParser = easyLogParser;
    }

    @Resource
    private ApplicationContext context;

    /**
     * 定义切点
     */
    @Pointcut("@annotation(recordLog))")
    public void pointCut(RecordLog recordLog) {
    }

    /**
     * 环绕通知
     *
     * @param joinPoint 通知点
     * @return obj
     */
    @Around(value = "pointCut(recordLog)", argNames = "joinPoint, recordLog")
    public Object around(ProceedingJoinPoint joinPoint, RecordLog recordLog) throws Throwable {
        Method method = ((MethodSignature) joinPoint.getSignature()).getMethod();
        Object[] args = joinPoint.getArgs();
        Class<?> targetClass = AopUtils.getTargetClass(joinPoint.getTarget());

        List<String> expressTemplate = getExpressTemplate(recordLog);

        Map<String, String> customFunctionExecResultMap = easyLogParser.processBeforeExec(expressTemplate, method, args, targetClass);

        Object result = null;
        MethodExecuteResult executeResult = new MethodExecuteResult(true);
        try {
            result = joinPoint.proceed();
            executeResult.calcExecuteTime();
        } catch (Throwable e) {
            executeResult.exception(e);
        }

        Object object = getResult(joinPoint, recordLog, recordLog.extendField().primaryKey());
        Map<String, Object> oldMap = new HashMap<>();
        if (recordLog.extendField().isCompare()) {
            // 存储修改前的对象
            oldMap = objectToMap(object);
        }
        switch (recordLog.type()) {
            case RecordLog.Type.INSERT:
                Map<String, Object> dataMap = objectToMap(object);
                log.info("新增的数据: {}", dataMap.toString());
                break;
            case RecordLog.Type.UPDATE:
                if (recordLog.extendField().isCompare()) {
                    // 比较新数据与数据库原数据
                    List<Map<String, Object>> list = defaultDealUpdate(object, oldMap, recordLog.extendField().primaryKey());
                    for (Map<String, Object> map : list) {
                        FieldInfo changeRecord = FieldInfo.builder()
                                .changeField(String.valueOf(map.get("filedName")))
                                .beforeChange(String.valueOf(map.get("oldValue")))
                                .afterChange(String.valueOf(map.get("filedName")))
                                .typeId(Long.parseLong(String.valueOf(map.get(recordLog.extendField().primaryKey()))))
                                .build();
                        String remark = "修改" + changeRecord.getChangeField() + "为" + changeRecord.getAfterChange() + ",原" + changeRecord.getChangeField() + "为" + (StringUtil.isBlank(changeRecord.getBeforeChange()) ? "空" : changeRecord.getBeforeChange());
                        changeRecord.setRemark(remark);
                        log.info("变更字段记录: {}", JsonUtil.toJSONString(changeRecord));
                    }
                }
            case RecordLog.Type.DELETE:
                log.info("删除的数据: {}", oldMap);
            default:
        }

        if (!executeResult.isSuccess() && ObjectUtils.isEmpty(recordLog.fail())) {
            log.warn("[{}] 方法执行失败，EasyLog 失败模板没有配置", method.getName());
        } else {
            Map<String, String> templateMap = easyLogParser.processAfterExec(expressTemplate, customFunctionExecResultMap, method, args, targetClass, executeResult.getErrMsg(), result);
            sendLog(recordLog, result, executeResult, templateMap);
        }
        // 抛出异常
        if (!executeResult.isSuccess()) {
            throw executeResult.getThrowable();
        }
        return result;
    }

    private List<Map<String, Object>> defaultDealUpdate(Object newObject, Map<String, Object> oldMap, String tableId) {
        log.info("旧数据:{}", oldMap);
        try {
            List<Map<String, Object>> list = new ArrayList<>();
            Map<String, Object> newMap = objectToMap(newObject);
            oldMap.forEach((k, v) -> {
                Object newResult = newMap.get(k);
                if (null != v && !v.equals(newResult)) {
                    Field field = ReflectionUtil.getAccessibleField(newObject, k);
                    assert field != null;

                    Schema schema = field.getAnnotation(Schema.class);
                    if (null != schema && StringUtil.isNotEmpty(schema.description())) {
                        // 翻译表达式 0=男,1=女
                        Map<String, Object> result = new HashMap<>();
                        result.put("filedName", schema.description());
                        result.put(tableId, newMap.get(tableId));
                        result.put("oldValue", v);
                        result.put("newValue", newResult);

                        list.add(result);
                    }
                }
            });
            log.info("比较的数据:{}", list);
            return list;
        } catch (Exception e) {
            log.error("比较异常", e);
            e.printStackTrace(System.err);
            throw new RuntimeException("比较异常", e);
        }
    }

    public Object getResult(JoinPoint joinPoint, RecordLog recordLog, String tableId) {
        Object info = joinPoint.getArgs()[0];
        Object id = ReflectionUtil.getFieldValue(info, tableId);
        Assert.notNull(id, "id不能为空");
        Class<?> idType = recordLog.extendField().idType();
        if (idType.isInstance(id)) {
            Class<?> cls = recordLog.extendField().service();
            IService<?> service = (IService<?>) context.getBean(cls);
            return service.getById((Serializable) id);
        } else {
            throw new RuntimeException("请核实id type");
        }
    }

    private Map<String, Object> objectToMap(Object obj) {
        if (obj == null) {
            return null;
        }
        return BeanUtil.beanToMap(obj);
    }

    /**
     * 发送日志
     *
     * @param recordLog     日志注解
     * @param result        结果
     * @param executeResult 方法执行结果
     * @param templateMap   模板map
     */
    private void sendLog(RecordLog recordLog, Object result, MethodExecuteResult executeResult, Map<String, String> templateMap) {
        RecordLogInfo easyLogInfo = createEasyLogInfo(templateMap, recordLog, executeResult);
        if (easyLogInfo != null) {
            easyLogInfo.setResult(JsonUtil.toJSONString(result));
            log.info("【logRecord】log={}", JsonUtil.toJSONString(easyLogInfo));
            if (recordLog.type().isSave()) {
                // 日志插入数据库
                logRecordService.record(easyLogInfo);
            }
        }
    }

    /**
     * 创建操作日志实体
     *
     * @param templateMap 模板map
     * @param recordLog   日志注解
     * @return easyLogInfo
     */
    private RecordLogInfo createEasyLogInfo(Map<String, String> templateMap, RecordLog recordLog, MethodExecuteResult executeResult) {
        // 记录条件为 false，则不记录
        if ("false".equalsIgnoreCase(templateMap.get(recordLog.extendField().condition()))) {
            return null;
        }
        RecordLogInfo easyLogInfo = new RecordLogInfo();
        String operator = templateMap.get(recordLog.operator());
        if (ObjectUtils.isEmpty(operator)) {
            operator = operatorService.getOperator();
        }
        easyLogInfo.setModule(recordLog.module());
        easyLogInfo.setType(recordLog.type().getValue());
        easyLogInfo.setOperator(operator);

        easyLogInfo.setContent(executeResult.isSuccess() ? templateMap.get(recordLog.success()) : templateMap.get(recordLog.fail()));
        easyLogInfo.setSuccess(executeResult.isSuccess());
        easyLogInfo.setErrorMsg(executeResult.getErrMsg());
        easyLogInfo.setExecuteTime(executeResult.getExecuteTime());
        easyLogInfo.setOperateTime(executeResult.getOperateTime());

        return easyLogInfo;
    }


    /**
     * 获取不为空的待解析模板
     *
     * @param recordLog 日志注解
     * @return List
     */
    private List<String> getExpressTemplate(RecordLog recordLog) {
        List<String> list = CollectUtil.newArrayList(recordLog.title(), recordLog.operator(), recordLog.success(), recordLog.fail(), recordLog.extendField().condition());
        return list.stream().filter(s -> !ObjectUtils.isEmpty(s)).collect(Collectors.toList());
    }
}
