package com.example.dztest.service.interfaces.chat;

import com.example.dztest.common.annotations.*;
import com.example.dztest.domain.HttpResponse;
import com.example.dztest.utils.uilogger.UILogger;

@Mapping(path = "/chat")
public interface ChatApi {
    @UILogger(desc = "问题提问")
    @Get(path = "/api/v1/chat/dm/questionFill", description = "问题提问")
    HttpResponse questionFill(
            @Param("ask") String ask,
            @Param("sessionId") String sessionId,
            @Param("LOCAL_TOKEN") String LOCAL_TOKEN
    );

//    @Get(path = "/api/v1/tags/list", description = "标签列表")
//    HttpResponse tagList(
//            @Param("robotId") String robotId,
//            @Param("orderBy") String orderBy,
//            @Param("orderByType") String orderByType
//    );
//
//    @Get(path = "/api/v1/sms/list", description = "对话短信列表")
//    HttpResponse smsList(
//            @Param("robotId") String robotId
//    );
//
//    @Get(path = "/api/v1/blacklistRule/list", description = "黑名单规则列表")
//    HttpResponse blackRuleList(
//            @Param("robotId") String robotId
//    );

    @Post(path = "/api/v1/chat/initSession", description = "初始化会话")
    HttpResponse initSession(
            @FormData("token") String token
    );

    @Post(path = "/api/v1/chat/initSession", description = "初始化c端会话")
    HttpResponse initCSession(
            @FormData("token") String token,
            @FormData("LOCAL_TOKEN") String LOCAL_TOKEN
    );

    @Post(path = "/api/v1/chat/initSession", description = "初始化窗口测试会话")
    HttpResponse initWindowSession(
            @Param("testOnly") boolean testOnly,
            @Header("Authorization") String Authorization,
            @FormData("ACCESS_PATH") String ACCESS_PATH,
            @FormData("token") String token,
            @FormData("tenantId") String tenantId,
            @FormData("robotId") String robotId,
            @FormData("LOCAL_TOKEN") String LOCAL_TOKEN
    );

    @UILogger(desc = "c端测试问答知识")
    @Post(path = "/api/v1/chat/test", description = "测试问答知识")
    HttpResponse test(
            @Raw("$.ask") String ask,
            @Raw("$.token") String token,
            @Raw("$.tenantId") String tenantId,
            @Raw("$.robotId") String robotId,
            @Raw("$.sessionId") String sessionId,
            @Raw("$..LOCAL_TOKEN") String LOCAL_TOKEN
    );

    @UILogger(desc = "测试窗测试问答知识")
    @Post(path = "/api/v1/chat/test", description = "测试窗测试问答测试")
    HttpResponse testWindow(
            @Raw("$.ask") String ask,
            @Raw("$.extParm.token") String token,
            @Raw("$.extParm.tenantId") String tenantId,
            @Raw("$.extParm.robotId") String robotId,
            @Raw("$.extParm.LOCAL_TOKEN") String LOCAL_TOKEN,
            @Raw("$.sessionId") String sessionId,
            @Raw("$.LOCAL_TOKEN") String LOCAL_TOKEN1
    );

    @UILogger(desc = "c端问答 ")
    @Post(path = "/api/v1/chat/answer", description = "c端问答接口")
    HttpResponse answer(
            @Raw("$.ask") String ask,
            @Raw("$.extParm.token") String token,
            @Raw("$.extParm.LOCAL_TOKEN") String LOCAL_TOKEN,
            @Raw("$.sessionId") String sessionId,
            @Raw("$.LOCAL_TOKEN") String LOCAL_TOKEN1
    );

}
