package com.example.dztest.service.interfaces.imchat;


import com.example.dztest.common.annotations.*;
import com.example.dztest.domain.HttpResponse;

/*
 * 路由接口
 * */
@Mapping(path="/imchat")
public interface RouterControllerApi {

    @Post(path = "/api/v1/router/end/session", description = "客服结束会话")
    HttpResponse endSession(@Param("sessionId") String sessionId);

}
