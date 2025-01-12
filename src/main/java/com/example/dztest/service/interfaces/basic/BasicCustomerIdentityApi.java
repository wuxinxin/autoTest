package com.example.dztest.service.interfaces.basic;

import com.example.dztest.common.annotations.*;
import com.example.dztest.domain.HttpResponse;

/*
* 客户服务
* */
@Mapping(path = "/basic", url = "${url.basic}")
public interface BasicCustomerIdentityApi {
    @Post(path = "/api/v1/customer/identity", description = "手动添加客户")
    HttpResponse addCustomer(@Raw("$.name") String name,
                            @Raw("$.basicCustomerDictList[0].value") String value);

    @Post(path = "/api/v1/customer/identity", description = "手动添加客户")
    HttpResponse addCustomerByType(@Raw("$.name") String name,
                             @Raw("$.basicCustomerDictList[0].value") String value,
                             @Raw("$.basicCustomerDictList[1].value") String customerType
    );

    @Post(path = "/api/v1/customer/identity", description = "手动添加客户")
    HttpResponse addCustomerByPhone(@Raw("$.name") String name,
                                   @Raw("$.phone") String phone
    );
    @Get(path = "/api/v1/customer/desensitive",description = "查询客户信息")
    HttpResponse queryCustomerByPhone(@Param("searchStr") String searchStr);

}
