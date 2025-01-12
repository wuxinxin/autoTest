package com.example.dztest.apicase.function;

import java.util.Calendar;
import java.util.Date;

/**
 * @description: 获取当前时间截
 * @author xinxin.wu
 * @date 2023/12/15
 * @version: 1.0
 */
public class TimeCurrentFunction implements Function {

    @Override
    public String execute(String[] args) {
        long timestamp = System.currentTimeMillis();
        return String.valueOf(timestamp);
    }

    @Override
    public String getReferenceKey() {
        return "timeCurrent";
    }

}
