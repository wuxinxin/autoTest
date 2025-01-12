package com.example.dztest.service.interfaces.admin;

import com.example.dztest.common.annotations.Get;
import com.example.dztest.common.annotations.Mapping;
import com.example.dztest.common.annotations.Param;
import com.example.dztest.domain.HttpResponse;
@Mapping(path = "/kms", url = "admin")
public interface AdminKmsApi {
    @Get(path = "/api/v1/robotLimit/getRobotLimit", description = "获取机器人数量信息")
    HttpResponse getRobotLimit(@Param("tenantId") String tenantId);

}
