package com.example.dztest.apicase.function;

import com.example.dztest.utils.TimeUtil;

/**
 * @author xinxin.wu
 * @description: 内置函数，获取当前时间戳
 * @date 2023/10/18
 * @version: 1.0
 */
public class TimeFunction implements Function {
    @Override
    public String execute(String[] args) {
        String dateStyple = args[0];
        return TimeUtil.getNowTime(dateStyple);
    }

    @Override
    public String getReferenceKey() {
        return "time";
    }

}
