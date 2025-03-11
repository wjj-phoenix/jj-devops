package com.phoenix.devops.annotation;

import com.mybatisflex.core.service.IService;
import lombok.Getter;

import java.lang.annotation.*;

/**
 * 日志记录注解
 */
@Documented
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface RecordLog {
    /**
     * 模块名
     *
     * @return module
     */
    String module() default "";

    /**
     * 日志标题
     *
     * @return title
     */
    String title();

    /**
     * 操作者
     *
     * @return operator
     */
    String operator() default "";

    /**
     * 操作类型：比如增删改查
     *
     * @return type
     */
    Type type() default Type.UNKNOWN;

    /**
     * 成功模板
     *
     * @return success
     */
    String success();

    /**
     * 失败模板
     *
     * @return fail
     */
    String fail() default "";

    /**
     * 扩展字段
     * @return extendField
     */
    ExtendField extendField() default @ExtendField();

    @Documented
    @Target(ElementType.FIELD)
    @Retention(RetentionPolicy.RUNTIME)
    @interface ExtendField {
        // 查询数据库所调用的class文件 selectById方法所在的Service类
        Class<? extends IService> service() default IService.class;

        /**
         * 是否需要比较新旧数据
         *
         * @return true|false
         */
        boolean isCompare() default true;

        /**
         * 主键的数据类型
         *
         * @return 主键类型
         */
        Class<?> idType() default Long.class;

        /**
         * 操作对象的主键字段名称
         *
         * @return 主键字段名称
         */
        String primaryKey() default "id";

        /**
         * 记录条件 默认 true
         * @return 条件
         */
        String condition() default "";
    }


    @Getter
    enum Type {
        LOGIN(1, "登录", true),
        SELECT(2, "查询", false),
        INSERT(3, "新增", true),
        UPDATE(4, "修改", true),
        DELETE(5, "删除", true),
        UNKNOWN(0, "未知", false);

        private final int code;
        private final String value;
        private final boolean isSave;

        Type(int code, String value, boolean isSave) {
            this.code = code;
            this.value = value;
            this.isSave = isSave;
        }
    }
}
