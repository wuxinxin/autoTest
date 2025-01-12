package com.example.dztest.service.interfaces.speech;

import com.example.dztest.common.annotations.*;
import com.example.dztest.domain.HttpResponse;

@Mapping(path = "/speech", url = "${url.speech}")
public interface TenantDictApi {
    @Get(path = "/api/v1/dict/autobusy", description = "查询自动置忙配置")
    HttpResponse getAutobusy();

    @Post(path = "/api/v1/dict/autobusy", description = "保存自动置忙配置")
    HttpResponse saveAutobusy(@Param("status") Integer status,
                              @Param("vaue") Integer value);
}
