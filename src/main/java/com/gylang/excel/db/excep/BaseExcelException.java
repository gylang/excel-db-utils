package com.gylang.excel.db.excep;

import lombok.Getter;
import lombok.Setter;

/**
 * 异常
 *
 * @author gylang
 * data 2020/7/4
 * @version v0.0.1
 */
@Setter
@Getter
public class BaseExcelException extends BaseException {
    public BaseExcelException(String code, String msg) {
        super(code, msg);
    }

    public BaseExcelException(String message, String code, String msg) {
        super(message, code, msg);
    }

    public BaseExcelException(String message, Throwable cause, String code, String msg) {
        super(message, cause, code, msg);
    }

    public BaseExcelException(Throwable cause, String code, String msg) {
        super(cause, code, msg);
    }

    public BaseExcelException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace, String code, String msg) {
        super(message, cause, enableSuppression, writableStackTrace, code, msg);
    }
}

