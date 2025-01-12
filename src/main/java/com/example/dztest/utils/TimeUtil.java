package com.example.dztest.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class TimeUtil {

    public static void sleep(Integer s) {
        try {
            Thread.sleep(s * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static String getNowTime() {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
        Date date = new Date(System.currentTimeMillis());
        return formatter.format(date);
    }

    public static String getNowTime(String dateStyple) {
        SimpleDateFormat formatter = new SimpleDateFormat(dateStyple);
        Date date = new Date(System.currentTimeMillis());
        return formatter.format(date);
    }

    /**
     * 当前时间加年，月，日，小时，分钟，秒；
     * @param dateStyple 时间格式;注意HH返回一天中小时数，hh返回am/pm中小时数
     * @param type  时间类型，年，月，日，小时，分钟，秒，对应入参yy,MM,dd,hh,mm,ss
     * @param x 相隔时间数，可以是正数、负数
     * @return
     */
    public static String getNowTimeAdd(String dateStyple, String type, int x) {
        SimpleDateFormat formatter = new SimpleDateFormat(dateStyple);
        Calendar calendar = Calendar.getInstance();
        switch (type) {
            case "yy":
                calendar.add(Calendar.YEAR, x);
                break;
            case "MM":
                calendar.add(Calendar.MONTH, x);
                break;
            case "dd":
                calendar.add(Calendar.DATE, x);
                break;
            case "hh":
                calendar.add(Calendar.HOUR, x);
                break;
            case "mm":
                calendar.add(Calendar.MINUTE, x);
                break;
            case "ss":
                calendar.add(Calendar.SECOND, x);
                break;
            default:
                System.out.println("不能匹配");
        }

        return formatter.format(calendar.getTime());
    }
}
