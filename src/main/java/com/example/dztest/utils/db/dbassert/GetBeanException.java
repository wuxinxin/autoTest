package com.example.dztest.utils.db.dbassert;

/**
 * @ClassName: GetBeanException
 * @author: jian.ma@msxf.com
 * @create: 2022/6/7
 * @update: 2022/6/7
 **/

public class GetBeanException extends RuntimeException {

    private String message;

    public GetBeanException(String message) {
        this.message = message;
    }

    public GetBeanException(String message, Throwable cause) {
        super(message, cause);
    }

    public GetBeanException(Throwable cause) {
        super(cause);
    }

    protected GetBeanException(String message, Throwable cause,
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
