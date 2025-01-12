package com.example.dztest.service.interfaces.outbound;

import com.example.dztest.common.annotations.Get;
import com.example.dztest.common.annotations.Mapping;
import com.example.dztest.common.annotations.Post;
import com.example.dztest.common.annotations.Raw;
import com.example.dztest.domain.HttpResponse;

@Mapping(path = "/outbound")
public interface SpeakFlowV2ControllerApi {


    /*会话初始化接口,test使用*/
    @Post(path = "/api/v2/outbound/flow/initSessionTest/a", description = "会话初始化接口")
    HttpResponse initSessionTest(@Raw("$.robotId") String robotId,
                                 @Raw("$.tenantId") String tenantId);

    /*会话初始化接口,test使用*/
    @Post(path = "/api/v2/outbound/flow/initSessionTest/a", description = "会话初始化接口")
    HttpResponse initSessionTest2(@Raw("$.robotId") String robotId,
                                  @Raw("$.tenantId") String tenantId,
                                  @Raw("$.chatLanguageId") Integer chatLanguageId,
                                  @Raw("$.autoTranslate") Boolean autoTranslate);

    /*会话初始化接口,test使用*/
    @Post(path = "/api/v2/outbound/flow/initSessionTest/a", description = "会话初始化接口")
    HttpResponse initSessionTest3(@Raw("$.robotId") String robotId,
                                  @Raw("$.tenantId") String tenantId,
                                  @Raw("$.autoTranslate") Boolean autoTranslate);

    /*会话应答接口-根据问题获取答案,test使用, first_call*/
    @Post(path = "/api/v2/outbound/flow/answerTest/a", description = "会话应答接口-根据问题获取答案")
    HttpResponse answerTest(@Raw("$.callRecordId") String callRecordId,
                            @Raw("$.flowCode") String flowCode,
                            @Raw("$.sessionId") String sessionId,
                            @Raw("$.ask") String ask);


    /*会话应答接口-根据问题获取答案,test使用*/
    @Post(path = "/api/v2/outbound/flow/answerTest/a", description = "会话应答接口-根据问题获取答案")
    HttpResponse answerTest(@Raw("$.callRecordId") String callRecordId,
                            @Raw("$.sessionId") String sessionId,
                            @Raw("$.ask") String ask);


}
