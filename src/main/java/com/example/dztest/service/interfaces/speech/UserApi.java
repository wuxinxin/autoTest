package com.example.dztest.service.interfaces.speech;

import com.example.dztest.common.annotations.Get;
import com.example.dztest.common.annotations.Mapping;
import com.example.dztest.common.annotations.Param;
import com.example.dztest.domain.HttpResponse;

@Mapping(path = "/speech", url = "${url.speech}")
public interface UserApi {
    @Get(path = "/api/v1/forward/sub/select/user/info/a", description = "查询用户信息")
    HttpResponse getUserInfo(@Param("userTenantId") String userTenantId,
                             @Param("tenantId") String tenantId);
}
