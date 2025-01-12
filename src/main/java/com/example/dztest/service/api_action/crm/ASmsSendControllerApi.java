package com.example.dztest.service.api_action.crm;

import com.example.dztest.domain.HttpResponse;
import com.example.dztest.service.interfaces.crm.SmsSendControllerApi;
import com.example.dztest.utils.TimeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.net.URLDecoder;
import java.net.URLEncoder;

@Service
public class ASmsSendControllerApi {

    @Autowired
    private SmsSendControllerApi smsSendControllerApi;

    /*
     * 查询租户当天发送短信记录（(智能语音-通话记录-外呼记录）
     * */
    public HttpResponse getTodayRecordList() {
        String leftCallStartTime = TimeUtil.getNowTime("yyyy-MM-dd 00:00:00");
        String rightCallStartTime = TimeUtil.getNowTime("yyyy-MM-dd 23:59:59");
        return smsSendControllerApi.getSmsRecordAll(URLEncoder.encode(leftCallStartTime), URLEncoder.encode(rightCallStartTime));
    }
}
