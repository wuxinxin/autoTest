package com.example.dztest.service.interfaces.admin;

import com.example.dztest.common.annotations.*;
import com.example.dztest.domain.HttpResponse;

@Mapping(path = "/call", url = "admin")
public interface AdminCallApi {
    @Get(path = "/api/v1/admin/config/tenant/get", description = "获取呼叫中心坐席并发数")
    HttpResponse getSpeechUpperLimit(@Param("tenantId") String tenantId);

    @Post(path = "/api/v1/admin/outbound/insertGroup", description = "新建呼出号码组")
    HttpResponse newNumGroup(@Param("tenancyId") String tenancyId,
                             @Param("groupname") String groupname,
                             @Param("lineConcurrentLimit") Integer lineConcurrentLimit
    );

    @Get(path = "/api/v1/admin/outbound/numberGroups", description = "获取租户呼出号码组信息")
    HttpResponse getNumGroups(@Param("tenancyId") String tenancyId);
}
