package com.example.dztest.apicase.function;

import com.example.dztest.utils.TimeUtil;

/**
 * @author xinxin.wu
 * @description: 内置函数，当前时间加年，月，日，小时，分钟，秒；对应入参yy,MM,dd,hh,mm,ss;例子 __timeAdd(yyyyMMddhhmmss, mm, 5)
 * @date 2023/12/09
 * @version: 1.0
 */
public class TimeAddFunction implements Function {
    @Override
    public String execute(String[] args) {
        String dateStyple = args[0];
        String type = args[1];
        int x = Integer.parseInt(args[2]);
        return TimeUtil.getNowTimeAdd(dateStyple, type, x);
    }

    @Override
    public String getReferenceKey() {
        return "timeAdd";
    }

}
