package com.example.dztest.service.interfaces.outbound;

import com.example.dztest.common.annotations.*;
import com.example.dztest.domain.HttpResponse;

import java.util.List;

@Mapping(path = "/outbound")
public interface OutboundTaskManagerV2ControllerApi {

    /*外呼任务中，上传名单*/
    @Post(path = "/api/v3/outbound/task/manage/importList/{robotId}", description = "上传外呼名单")
    HttpResponse importList(@PathVariable("robotid") String robotid,
                            @FilePath("file") String file,
                            @Param("phoneValidateType") String phoneValidateType);

    /*外呼任务中，上传名单*/
    @Post(path = "/api/v3/outbound/task/manage/importListAndAck/{taskId}", description = "确认上传文件")
    HttpResponse importListAndAck(@PathVariable("taskId") Integer taskId,
                                  @Param("ackFileId") String ackFileId);


    /*对话任务列表（机器人及机器人下可用的流程信息）*/
    @Get(path = "/api/v2/outbound/task/manage/callTask/getDialogTask", description = "获取对话任务列表")
    HttpResponse getDialogTask();

    /*
     *创建外呼任务
     * @param taskName  任务名称
     * @param taskType  启动方式（1-手动启动、2-定时启动、3-循环任务）
     * @param dialogTaskId 任务流程id
     * @param outboundNoGroup 外呼号码组
     * @param aiSeatsNum AI座席数
     * @param fileId  名单id
     * @param callListTask  未知，与fiel一致即可
     * @param robotId  机器人id
     * */
    @Post(path = "/api/v2/outbound/task/manage/callTask", description = "创建外呼任务")
    HttpResponse createCallTask(
            @Raw("$.fileId") List<String> words,
            @Raw("$.taskName") String taskName,
            @Raw("$.taskType") Integer taskType,
            @Raw("$.dialogTaskId") String dialogTaskId,
            @Raw("$.outboundNoGroup") String outboundNoGroup,
            @Raw("$.aiSeatsNum") Integer aiSeatsNum,
            @Raw("$.fileId") String fileId,
            @Raw("$.robotId") String robotId);

    /*
     *获取外呼任务详情
     * */
    @Get(path = "/api/v2/outbound/task/manage/getCallTaskInfo/{taskId}", description = "获取外呼任务详情")
    HttpResponse getCallTaskInfo(@PathVariable("taskId") Integer taskId);

    /*
     *启动外呼任务
     * */
    @Post(path = "/api/v2/outbound/task/manage/callTask/start/{taskId}", description = "启动外呼任务")
    HttpResponse startCallTask(@PathVariable("taskId") Integer taskId);

    /*
     *外呼通话记录已完成列表
     * */
    @Post(path = "/api/v2/audio/record/listPage", description = "外呼通话记录已完成列表")
    HttpResponse getRecordListPage(@Raw("$.outboundCallTaskId") Integer outboundCallTaskId);

    /*
     *外呼通话记录已完成列表,赛选已接通
     * */
    @Post(path = "/api/v2/audio/record/listPage", description = "外呼通话记录已接通列表")
    HttpResponse getRecordListPageStatus7(@Raw("$.outboundCallTaskId") Integer outboundCallTaskId);

    /*
     *外呼通话记录已完成列表,赛选已接通;分页
     * */
    @Post(path = "/api/v2/audio/record/listPage", description = "外呼通话记录已接通列表")
    HttpResponse getRecordListPageStatus7Page(@Raw("$.outboundCallTaskId") Integer outboundCallTaskId,
                                              @Raw("$.pageNum") Integer pageNum,
                                              @Raw("$.pageSize") Integer pageSize);

}

