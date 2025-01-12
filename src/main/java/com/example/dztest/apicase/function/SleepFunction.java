package com.example.dztest.apicase.function;

import com.example.dztest.utils.TimeUtil;

/**
 * @description: 睡眠xx秒
 * @author xinxin.wu
 * @date 2023/11/01
 * @version: 1.0
 */
public class SleepFunction implements Function {

    @Override
    public String execute(String[] args) {
        String time = args[0];
        TimeUtil.sleep(Integer.valueOf(time));
        return "";
    }

    @Override
    public String getReferenceKey() {
        return "sleep";
    }

}
