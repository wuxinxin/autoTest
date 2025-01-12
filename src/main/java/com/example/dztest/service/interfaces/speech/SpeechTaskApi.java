package com.example.dztest.service.interfaces.speech;

import com.example.dztest.common.annotations.*;
import com.example.dztest.domain.HttpResponse;

@Mapping(path = "/speech", url = "${url.speech}")
public interface SpeechTaskApi {

    @Post(path = "/api/v1/outbound/list/upload", description = "上传外呼名单")
    HttpResponse uploadList(@FilePath("file") String file);

    @Post(path = "/api/v1/outbound/task/create", description = "创建外呼任务")
    HttpResponse createAutoOutboundTask(@Raw("$.taskName")String taskName,
                                    @Raw("$.fileId")String fileId,
                                    @Raw("$.outboundNumberGroupId") String outboundNumberGroupId,
                                    @Raw("$.skillSetId") String skillSetId,
                                    @Raw("$.taskExecEndDate") String  taskExecEndDate,
                                    @Raw("$.taskExecStartDate") String taskExecStartDate,
                                    @Raw("$.uploadFilename") String uploadFilename);

    @Post(path = "/api/v1/outbound/task/create", description = "创建外呼任务")
    HttpResponse createManualOutboundTask(@Raw("$.taskName")String taskName,
                                    @Raw("$.fileId")String fileId,
                                    @Raw("$.outboundNumberGroupId") String outboundNumberGroupId,
                                    @Raw("$..skillSetId") String skillSetId,
                                    @Raw("$..skillsetsId") String skillsetsId,
                                    @Raw("$.taskExecEndDate") String  taskExecEndDate,
                                    @Raw("$.taskExecStartDate") String taskExecStartDate,
                                    @Raw("$.uploadFilename") String uploadFilename,
                                    @Raw("$..basicUserId") Integer basicUserId);


    @Get(path = "/api/v1/sg/listSkillGroupByType", description = "获取外呼技能组信息")
    HttpResponse listSkillGroupByType(@Param("groupType") String groupType);

    @Get(path = "/api/v1/sg/listSkillGroupByType", description = "获取外呼技能组信息")
    HttpResponse listSkillGroupByType();

}
