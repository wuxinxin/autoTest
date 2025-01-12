package com.example.dztest.service.interfaces.basic;

import com.example.dztest.common.annotations.Get;
import com.example.dztest.common.annotations.Mapping;
import com.example.dztest.common.annotations.Param;
import com.example.dztest.domain.HttpResponse;

/*
 * 短信验证码服务
 * */
@Mapping(path="/basic", url = "${url.basic}")
public interface SmsCodeControllerApi {

    @Get(path = "/api/v1/smscode/login", description = "登录时发送手机验证码")
    HttpResponse getSmscode(@Param("mobile") String mobile);

}
