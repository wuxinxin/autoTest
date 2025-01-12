package com.example.dztest.utils.db.rollback.exception;

/**
 * 通用数据回滚异常类
 * @author jian.ma@msxf.com
 * @version 1.0
 * Created by jian.ma@msxf.com on 2022/06/11.
 */
public class RollBackException extends RuntimeException {

    private String message;

    public RollBackException(String message) {
        this.message = message;
    }

    public RollBackException(String message, Throwable cause) {
        super(message, cause);
    }

    public RollBackException(Throwable cause) {
        super(cause);
    }

    protected RollBackException(String message, Throwable cause,
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
