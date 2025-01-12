package com.example.dztest.service.interfaces.nlu;

import com.example.dztest.common.annotations.Get;
import com.example.dztest.common.annotations.Mapping;
import com.example.dztest.common.annotations.Param;
import com.example.dztest.domain.HttpResponse;


@Mapping(path = "/nlu")
public interface NluApi {
    @Get(path = "/api/v1/train/excute/train", description = "从回收站最终删除机器人")
    HttpResponse train(@Param("robotId") String robotId);

    @Get(path = "/api/v1/train/robot/progress", description = "模型训练进程查询")
    HttpResponse progress(@Param("robotId") String robotId,
                          @Param("modelIds") String modelIds);

    @Get(path = "/api/v1/model/train/history",description = "查询模型训练历史")
    HttpResponse history(@Param("robotId") String robotId);
}
