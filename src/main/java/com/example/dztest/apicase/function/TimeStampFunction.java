package com.example.dztest.apicase.function;

import javax.xml.crypto.Data;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;

/**
 * @description: 获取当天开始(入参 start)、结束时间截(入参 end)，如2023-11-28 00:00:00  2023-11-28 23:59:59
 * @author xinxin.wu
 * @date 2023/11/28
 * @version: 1.0
 */
public class TimeStampFunction implements Function {

    @Override
    public String execute(String[] args) {
        String first = args[0];

        Date today = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(today);

        if ("start".equals(first)) {
            calendar.set(Calendar.HOUR_OF_DAY, 00);
            calendar.set(Calendar.MINUTE, 00);
            calendar.set(Calendar.SECOND, 00);
        } else if ("end".equals(first)) {
            calendar.set(Calendar.HOUR_OF_DAY, 23);
            calendar.set(Calendar.MINUTE, 59);
            calendar.set(Calendar.SECOND, 59);
        } else {
            return null;
        }

        Date d = calendar.getTime();
        return String.valueOf(d.getTime());
    }

    @Override
    public String getReferenceKey() {
        return "timeStampToday";
    }
}
