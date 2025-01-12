package com.example.dztest.service.interfaces.crm;


import com.example.dztest.common.annotations.Get;
import com.example.dztest.common.annotations.Mapping;
import com.example.dztest.common.annotations.Param;
import com.example.dztest.domain.HttpResponse;

/*
 * 短信发送管理接口
 * */
@Mapping(path="/crm", url = "${url.crm}")
public interface SmsSendControllerApi {

    @Get(path = "/api/v1/sms/record/all", description = "根据用户ID查询对应发送短信记录")
    HttpResponse getSmsRecordAll(@Param("startTime") String startTime,
                                 @Param("endTime") String endTime);

}
