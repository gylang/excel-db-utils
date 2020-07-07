package com.gylang.excel.db.enums;

import lombok.Getter;

import java.math.BigDecimal;

/**
 * @author gylang
 * data 2020/7/4
 * @version v0.0.1
 */
@Getter
public enum  ExcelColType {

    /**
     * 字符串
     */
    STRING,

    /**
     * INT
     */
    INTEGER,

    /**
     * FLOAT
     */
    FLOAT,

    /**
     * DOUBLE
     */
    DOUBLE,

    /**
     * 接收类型可以为BigDecimal 或 String
     * 货币 (0,2)
     */
    MONEY,

    /**
     * 精准到 日 2020-07-04
     */
    DATE,

    /**
     * 2020-07-04 10:10:10
     */
    TIME,

    /**
     * 10:10:10
     */
    ONLY_TIME,


    /**
     * BIG_DECIMAL
     */
    BIG_DECIMAL,

    AUTO
}
