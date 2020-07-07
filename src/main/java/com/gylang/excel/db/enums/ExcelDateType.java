package com.gylang.excel.db.enums;

import lombok.Getter;

/**
 * @author gylang
 * data 2020/7/4
 * @version v0.0.1
 */
@Getter
public enum ExcelDateType {

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
    ONLY_TIME

}
