package com.example.dztest.apicase.function;

import com.example.dztest.utils.TimeUtil;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * @Author: ColaDaddy
 * @Date: 2023/12/28
 * @Desc: 处理入参时间需要Encode问题
 */
public class TimeEncodeFunction implements Function{

    @Override
    public String execute(String[] args) {
        String dateStyple = args[0];
        String time = TimeUtil.getNowTime(dateStyple);
        String encodeTime = null;
        try {
            encodeTime = URLEncoder.encode(time, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
        return encodeTime;
    }

    @Override
    public String getReferenceKey() {
        return "timeEncode";
    }
}
