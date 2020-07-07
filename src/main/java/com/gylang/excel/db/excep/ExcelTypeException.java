package com.gylang.excel.db.excep;

import lombok.Data;

/**
 * @author gylang
 * data 2020/7/4
 * @version v0.0.1
 */
public class ExcelTypeException extends BaseExcelException {
    public ExcelTypeException(String code, String msg) {
        super(code, msg);
    }

    public ExcelTypeException(String message, String code, String msg) {
        super(message, code, msg);
    }

    public ExcelTypeException(String message, Throwable cause, String code, String msg) {
        super(message, cause, code, msg);
    }

    public ExcelTypeException(Throwable cause, String code, String msg) {
        super(cause, code, msg);
    }

    public ExcelTypeException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace, String code, String msg) {
        super(message, cause, enableSuppression, writableStackTrace, code, msg);
    }
}
