package com.example.dztest.service.interfaces.outbound;

import com.example.dztest.common.annotations.*;
import com.example.dztest.domain.HttpResponse;

@Mapping(path = "/outbound")
public interface AudioRecordV2ControllerApi {

    /*
    * 任务-进行中名单信息获取
    * */
    @Get(path = "/api/v2/audio/record/getRunningTaskCustomerList", description = "任务-进行中名单信息获取")
    HttpResponse getRunningTaskCustomerList(@Param("outboundCallTaskId") Integer outboundCallTaskId);

    /*
     * 获取音频播放url&录音内容
     * */
    @Get(path = "/api/v2/audio/record/audioUrlAndContents", description = "获取音频播放url&录音内容")
    HttpResponse getAudioUrlAndContents(@Param("sessionId") String sessionId);


    /*
     * 外呼拨打详情信息
     * */
    @Post(path = "/api/v2/audio/record/callDetail", description = "外呼拨打详情信息")
    HttpResponse getRecordCallDetail(@Raw("$.id") Integer recordId);

    /*
     * 呼入拨打详情信息
     * */
    @Post(path = "/api/v2/audio/record/callDetailIn", description = "呼入拨打详情信息")
    HttpResponse getRecordCallDetailIn(@Raw("$.id") Integer recordId);

}
