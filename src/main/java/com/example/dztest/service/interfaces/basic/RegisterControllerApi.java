package com.example.dztest.service.interfaces.basic;


import com.example.dztest.common.annotations.Delete;
import com.example.dztest.common.annotations.Mapping;
import com.example.dztest.common.annotations.Param;
import com.example.dztest.common.annotations.Post;
import com.example.dztest.domain.HttpResponse;

/*
 * 官网注册相关接口
 * */
@Mapping(path="/basic", url = "${url.basic}")
public interface RegisterControllerApi {

    @Post(path = "/api/v2/register/sms/send", description = "官网注册账号时发送手机验证码")
    HttpResponse sendSms(@Param("mobile") String mobile,
                         @Param("captcha") String captcha);

    @Post(path = "/api/v2/register/sms/validate", description = "验证手机短信")
    HttpResponse validateSms(@Param("phone") String phone,
                             @Param("code") String code);

    @Post(path = "/api/v2/register/complete", description = "完善注册信息并激活")
    HttpResponse complete(@Param("companyName") String companyName,
                          @Param("email") String email,
                          @Param("password") String password,
                          @Param("domain") String domain);

}
