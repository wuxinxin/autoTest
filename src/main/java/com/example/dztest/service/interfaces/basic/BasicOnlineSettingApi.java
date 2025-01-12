package com.example.dztest.service.interfaces.basic;

import com.example.dztest.common.annotations.*;
import com.example.dztest.domain.HttpResponse;

/*
 * 在线客服在线设置
 * */
@Mapping(path = "/basic", url = "${url.basic}")
public interface BasicOnlineSettingApi {
    @Get(path = "/api/v1/tenant/dict", description = "获取在线基础配置")
    HttpResponse getOnlineSetting();

    @Put(path = "/api/v1/tenant/dict/batch", description = "保存在线基础配置")
    HttpResponse saveOnlineSetting(
            @Raw("$.[0].value") String switchValue,
            @Raw("$.[0].tenantId") String tenantId0,
            @Raw("$.[1].tenantId") String tenantId1,
            @Raw("$.[2].tenantId") String tenantId2
    );
}

