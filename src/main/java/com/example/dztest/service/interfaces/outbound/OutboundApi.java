package com.example.dztest.service.interfaces.outbound;


import com.example.dztest.common.annotations.*;
import com.example.dztest.domain.HttpResponse;
import com.example.dztest.utils.uilogger.UILogger;

@Mapping(path = "/outbound")
public interface OutboundApi {

    @Get(path = "/api/v1/engine/admin/flow/subflow/conditionInitialConfig", description = "机器人详情")
    HttpResponse conditionInitialConfig(@Param("robotId") String robotId);

    @Post(path = "/api/v1/engine/admin/flow/listOutBoundMainFlows", description = "获取测试窗主流程流")
    HttpResponse listOutBoundMainFlows(@Param("robotId") String robotId);

    @UILogger(desc = "初始化测试会话")
    @Post(path = "/api/v2/outbound/flow/initSessionTest/a", description = "初始化测试会话")
    HttpResponse initSessionTest(
            @Raw("$.robotId") String robotId,
            @Raw("$.tenantId") String tenantId
    );

    @UILogger(desc = "测试窗问答参数为默认参数ask为first_call")
    @Post(path = "/api/v2/outbound/flow/answerTest/a", description = "测试窗问答参数为默认参数ask为first_call")
    HttpResponse answerTest(
            @Raw("$.callRecordId") String callRecordId,
            @Raw("$.sessionId") String sessionId,
            @Raw("$.flowCode") String flowCode
    );

    @UILogger(desc = "测试窗问答参数为默认参数ask为用户自定义")
    @Post(path = "/api/v2/outbound/flow/answerTest/a", description = "测试窗问答参数为默认参数ask为用户自定义")
    HttpResponse answerTestQ(
            @Raw("$.ask") String ask,
            @Raw("$.callRecordId") String callRecordId,
            @Raw("$.sessionId") String sessionId
    );

    //TODO 整个同名参数需要重复占位的问题
    @UILogger(desc = "画任务流程图")
    @Put(path = "/api/v1/engine/admin/flow/subflow/draft", description = "画任务流程图")
    HttpResponse draft(
            @Raw("$.previousCanvas.tenantId") String tenantId,
            @Raw("$..mainFlowId") String mainFlowId,
            @Raw("$..flowId") String flowId
    );

    @UILogger(desc = "新建任务流程")
    @Post(path = "/api/v1/engine/admin/flow/mainFlow", description = "新建任务流程")
    HttpResponse mainFlow(
            @Raw("$.robotId") String robotId
    );

    @Get(path = "/api/v1/engine/admin/flow/subflow/getListing", description = "获取流程图信息")
    HttpResponse getListing(@Param("mainFlowId") String mainFlowId);

    @UILogger(desc = "发布任务流程")
    @Post(path = "/api/v1/engine/admin/flow/mainFlow/publish", description = "发布任务流程")
    HttpResponse publish(@Param("mainFlowId") String mainFlowId);


    @UILogger(desc = "激活任务流程")
    @Post(path = "/api/v1/engine/admin/flow/mainFlow/activeOp", description = "激活任务流程")
    HttpResponse activeOp(
            @Raw("$.flowId") String flowId,
            @Raw("$.robotId") String robotId
    );

    @UILogger(desc = "设置为主流程")
    @Post(path = "/api/v1/engine/admin/flow/updateMainFlowMark", description = "设置为主流程")
    HttpResponse updateMainFlowMark(
            @Raw("$.flowIds[0]") String flowId1,
            @Raw("$.flowIds[1]") String flowId2,
            @Raw("$.robotId") String robotId
    );

    @Post(path = "/api/v1/callList/manage/batchCopyCallListFromRunningAudioRecords", description = "从任务拷贝名单到指定任务")
    HttpResponse batchCopyCallListFromRunningAudioRecords(
            @Raw("$.sourceTaskId") Integer sourceTaskId,
            @Raw("$.targetTaskId") Integer targetTaskId
    );

}
