package com.gylang.excel.db.annotation;

import com.gylang.excel.db.entity.DefaultType;

import java.lang.annotation.*;

/**
 * @author gylang,
 * date 2020/4/26,
 * @version 1.0
 */
@Documented
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ExcelField {
    /**
     * 列名
     *
     * @return 列名
     */
    String name() default "";

    Class<?> type() default DefaultType.class;
}
