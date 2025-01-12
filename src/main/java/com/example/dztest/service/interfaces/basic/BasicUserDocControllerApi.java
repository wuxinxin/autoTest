package com.example.dztest.service.interfaces.basic;

import com.example.dztest.common.annotations.FormData;
import com.example.dztest.common.annotations.Mapping;
import com.example.dztest.common.annotations.Param;
import com.example.dztest.common.annotations.Post;
import com.example.dztest.domain.HttpResponse;

@Mapping(path = "/basic", url = "${url.basic}")
public interface BasicUserDocControllerApi {
    @Post(path = "/user/login", description = "用户登录")
    HttpResponse login(@Param("account") String account,
                       @Param("password") String password);

    @Post(path = "/user/login", description = "用户登录，手机验证码登陆")
    HttpResponse login(@Param("target") String target,
                       @Param("loginType") String loginType,
                       @Param("remember-me") boolean rememberme,
                       @Param("account") String account,
                       @Param("password") String password);

    @Post(path = "/user/logout", description = "用户登出")
    HttpResponse logout();
}
