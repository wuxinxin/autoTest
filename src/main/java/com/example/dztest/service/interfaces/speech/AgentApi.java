package com.example.dztest.service.interfaces.speech;

import com.example.dztest.common.annotations.*;
import com.example.dztest.domain.HttpResponse;
import com.example.dztest.utils.uilogger.UILogger;

@Mapping(path = "/speech", url = "${url.speech}")
public interface AgentApi {
    @UILogger(desc = "获得，坐席维度-拨号盘-自动接听开关")
    @Get(path = "/api/v1/agent/auto/answer/switch", description = "获得，坐席维度-拨号盘-自动接听开关")
    HttpResponse getSwitch();


    @UILogger(isPage = false, desc = "修改，坐席维度-拨号盘-自动接听开关")
    @Post(path = "/api/v1/agent/auto/answer/switch", description = "修改，坐席维度-拨号盘-自动接听开关")
    HttpResponse setSwitch(@Param("value") Boolean value);

    @UILogger(isPage = false, desc = "坐席维度-获取外呼任务下关联的客户列表分页查询-手动外呼")
    @Get(path = "/api/v1/agent/outboundTask/manual/customer/list/{value}", description = "坐席维度-获取外呼任务下关联的客户列表分页查询-手动外呼")
    HttpResponse getOutboundTaskCustomerList(@PathVariable("value") Long value);
}
