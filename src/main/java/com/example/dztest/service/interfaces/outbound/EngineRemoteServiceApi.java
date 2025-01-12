package com.example.dztest.service.interfaces.outbound;

import com.example.dztest.common.annotations.*;
import com.example.dztest.domain.HttpResponse;

@Mapping(path = "/engine")
public interface EngineRemoteServiceApi {

    /*查询未停用的流程任务列表
     * 23版本流程引擎拆分，把流程相关的接口单独拆成一个应用
     * 23版本+后就用新接口
     * */
    @Post(path = "/api/v1/engine/admin/flow/mainFlow/getListing", description = "通过流程id查询流程名称")
    HttpResponse getMainFlow(@Raw("$..robotId") String robotId);

    @Post(path = "/api/v1/engine/admin/flow/mainFlow/getListing", description = "通过流程id 和robotType查询流程名称")
    HttpResponse getListing(
            @Raw("$..robotId") String robotId,
            @Raw("$..robotType") String robotType
            );


}
