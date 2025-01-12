package com.example.dztest.service.interfaces.basic;

import com.example.dztest.common.annotations.Get;
import com.example.dztest.common.annotations.Mapping;
import com.example.dztest.common.annotations.Post;
import com.example.dztest.common.annotations.Raw;
import com.example.dztest.domain.HttpResponse;

/**
 *计费套餐-租户资源套餐订单管理
 */
@Mapping(path="/basic", url = "${url.basic}")
public interface MealOrderControllerApi {

    @Get(path = "/api/v1/meal/order/resource/total", description = "查询租户当前时间的资源可用总量")
    HttpResponse getResourceTotal();

}
