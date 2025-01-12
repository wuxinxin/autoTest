package com.example.dztest.service.interfaces.workorder;

import com.example.dztest.common.annotations.Mapping;
import com.example.dztest.common.annotations.Param;
import com.example.dztest.common.annotations.Post;
import com.example.dztest.common.annotations.Raw;
import com.example.dztest.domain.HttpResponse;

/*
 * 工单服务
 * */
@Mapping(path = "/workorder", url = "${url.workorder}")
public interface BasicWorkOrderApi {
    @Post(path = "/api/v1/order/add", description = "创建工单")
    HttpResponse createWorkOrder(
            @Raw("$.orderName") String orderName,
            @Raw("$.woOperateDtoList[0].userId") String userId,
            @Raw("$.customerName") String customerName,
            @Raw("$.customerPhone") String customerPhone
    );
}
