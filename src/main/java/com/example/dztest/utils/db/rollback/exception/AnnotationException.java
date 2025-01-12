package com.example.dztest.utils.db.rollback.exception;

/**
 * 通用注解异常类
 * @author jian.ma@msxf.com
 * @version 1.0
 *          Created by jian.ma@msxf.com on 2022/06/11.
 */
public class AnnotationException extends RuntimeException {

    private String message;

    public AnnotationException(String message) {
        this.message = message;
    }

    public AnnotationException(String message, Throwable cause) {
        super(message, cause);
    }

    public AnnotationException(Throwable cause) {
        super(cause);
    }

    protected AnnotationException(String message, Throwable cause,
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
