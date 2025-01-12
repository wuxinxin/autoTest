package com.example.dztest.service.interfaces.imchat;

import com.example.dztest.common.annotations.Get;
import com.example.dztest.common.annotations.Mapping;
import com.example.dztest.common.annotations.Post;
import com.example.dztest.domain.HttpResponse;

/*
* 在线客服会话管理
* */
@Mapping(path="/imchat")
public interface SessionControllerApi {

    @Get(path = "/api/v1/session/list/online", description = "获取客服进行中会话")
    HttpResponse getOnlineSession();

}
