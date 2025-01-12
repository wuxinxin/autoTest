package com.example.dztest.service.interfaces.basic;

import com.example.dztest.common.annotations.Get;
import com.example.dztest.common.annotations.Mapping;
import com.example.dztest.common.annotations.Param;
import com.example.dztest.domain.HttpResponse;

/*
* 认证服务
* */
@Mapping(path = "/basic", url = "${url.basic}")
public interface BasicAuthControllerApi {

    @Get(path = "/auth/captcha/image/code", description = "获取验证码,此接口只是提供给测试使用")
    HttpResponse getImageCode();

    @Get(path = "/auth/chat/token", description = "获取聊天Token")
    HttpResponse getChatToken(@Param("productId") String productId);
}
