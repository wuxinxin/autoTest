package com.example.dztest.service.interfaces.basic;

import com.example.dztest.common.annotations.Get;
import com.example.dztest.common.annotations.Mapping;
import com.example.dztest.common.annotations.Param;
import com.example.dztest.domain.HttpResponse;

/**
 * 测试窗相关接口
 */
@Mapping(path = "/basic")
public interface RobotTestApi {
    @Get(path = "/auth/token/chat/robottest", description = "测试窗信息获取")
    HttpResponse robotTest(@Param("robotId") String robotId);
}
