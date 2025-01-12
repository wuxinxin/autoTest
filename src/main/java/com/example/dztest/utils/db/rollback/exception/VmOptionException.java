package com.example.dztest.utils.db.rollback.exception;

/**
 * VM Option异常类
 * @author jian.ma@msxf.com
 * @version 1.0
 *          Created by jian.ma@msxf.com on 2022/06/13.
 */
public class VmOptionException extends RuntimeException {

    private String message;

    public VmOptionException(String message) {
        this.message = message;
    }

    public VmOptionException(String message, Throwable cause) {
        super(message, cause);
    }

    public VmOptionException(Throwable cause) {
        super(cause);
    }

    protected VmOptionException(String message, Throwable cause,
                                boolean enableSuppression,
                                boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
