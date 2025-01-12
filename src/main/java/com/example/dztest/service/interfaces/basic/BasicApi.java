package com.example.dztest.service.interfaces.basic;

import com.example.dztest.common.annotations.Get;
import com.example.dztest.common.annotations.Mapping;
import com.example.dztest.common.annotations.Param;
import com.example.dztest.common.annotations.Post;
import com.example.dztest.domain.HttpResponse;


@Mapping(path = "/basic", url = "${url.basic}")
public interface BasicApi {
    @Post(path = "/user/login", description = "用户登录")
    HttpResponse login(@Param("account") String account,
                       @Param("password") String password);

    @Get(path = "/api/v1/tenant/info",description = "获取用户tenant信息")
    HttpResponse getTenantInfo();

}
