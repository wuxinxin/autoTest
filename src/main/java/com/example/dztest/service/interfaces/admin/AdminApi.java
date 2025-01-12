package com.example.dztest.service.interfaces.admin;

import com.example.dztest.common.annotations.*;
import com.example.dztest.domain.HttpResponse;


@Mapping(path = "/admin", url = "admin")
public interface AdminApi {

    @Post(path = "/user/login", description = "A端用户登录")
    HttpResponse login(@Param("account") String account,
                       @Param("password") String password);

    @Get(path = "/api/v1/admin/companyManage/list",description = "查询租户信息")
    HttpResponse tenantQuery(@Param("searchWord") String searchWord);

    @Get(path = "/api/v1/admin/companyResourceManage/query/companyResources", description = "获取租户资源信息")
    HttpResponse resourceQuery(@Param("tenantId") String tenantId,
                               @Param("global") Integer global);
    @Post(path = "/api/v1/admin/companyResourceManage/save",description = "配置租户资源")
    HttpResponse companyResourceManage(@Raw("companyName") String companyName,
                                       @Raw("id") String id,
                                       @Raw("tenantId") String tenantId
                                       );
}
