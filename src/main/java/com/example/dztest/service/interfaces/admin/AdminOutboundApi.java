package com.example.dztest.service.interfaces.admin;

import com.example.dztest.common.annotations.Get;
import com.example.dztest.common.annotations.Mapping;
import com.example.dztest.common.annotations.Param;
import com.example.dztest.domain.HttpResponse;
@Mapping(path = "/outbound", url = "admin")
public interface AdminOutboundApi {
    @Get(path = "/api/v1/charges/get", description = "获取外呼机器人坐席上限信息")
    HttpResponse getCallRobotLimit(@Param("tenantId") String tenantId);
}
