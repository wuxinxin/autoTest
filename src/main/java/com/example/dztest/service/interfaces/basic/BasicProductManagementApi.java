package com.example.dztest.service.interfaces.basic;

import com.example.dztest.common.annotations.*;
import com.example.dztest.domain.HttpResponse;
import com.example.dztest.utils.uilogger.UILogger;

/*
* 产品管理
* */
@Mapping(path="/basic", url = "${url.basic}")
public interface BasicProductManagementApi {

    @UILogger(desc = "创建渠道、添加产品",isPage = false)
    @Post(path = "/api/v1/user/product/create", description = "添加产品")
    HttpResponse addProduct(@Param("productName") String productName);

    @UILogger(desc = "停用渠道、停用产品")
    @Post(path = "/api/v1/user/product/freeze", description = "停用产品")
    HttpResponse stopProduct(@Param("productId") String productId);

    @UILogger(desc = "启用渠道、启用产品")
    @Post(path = "/api/v1/user/product/enable", description = "启用产品")
    HttpResponse openProduct(@Param("productId") String productId);

    @UILogger(desc = "删除渠道、删除产品")
    @Post(path = "/api/v1/user/product/delete", description = "删除产品")
    HttpResponse delProduct(@Param("productId") String productId);

//    @UILogger(desc = "查询渠道，查询产品")
    @Get(path = "/api/v1/user/product/batchQuery", description = "模糊查询产品")
    HttpResponse getProduct(@Param("searchKey") String searchKey,
                            @Param("source") String source_web);
}
