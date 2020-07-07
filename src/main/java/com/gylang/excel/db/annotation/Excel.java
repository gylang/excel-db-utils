package com.gylang.excel.db.annotation;

import java.lang.annotation.*;

/**
 * @author gylang,
 * date 2020/4/26,
 * @version 1.0
 */
@Documented
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface Excel {

    /**
     * excel文件路径
     * @return 文件路径
     */
    String path();

    /**
     * sheetName
     * @return sheetName
     */
    String sheetName() default "";

    /**
     * excel名
     * @return excel名
     */
    String excelName() default "";
}
