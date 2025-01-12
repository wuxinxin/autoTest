package com.example.dztest.utils.excel.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * ExcelConfigException
 *
 * @author jian.ma@msxf.com
 * @version 1.0
 * Created by jian.ma@msxf.com on 2022/06/25.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExcelConfigException extends RuntimeException {
    private String message;
    private Throwable cause;
    private boolean enableSuppression;
    boolean writableStackTrace;

    public ExcelConfigException(String message) {
        this.message = message;
    }

    public ExcelConfigException(Throwable cause) {
        this.cause = cause;
    }

    public ExcelConfigException(String message, Throwable cause) {
        super(message, cause);
    }

}
