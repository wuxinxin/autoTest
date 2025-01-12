package com.example.dztest.service.interfaces.basic;


import com.example.dztest.common.annotations.*;
import com.example.dztest.domain.HttpResponse;

/*
 * 客户黑名单组关系服务
 * */
@Mapping(path = "/basic", url = "${url.basic}")
public interface BasicCustomerBlacklistGroupRelationControllerApi {

    @Post(path = "/api/v1/customer/blacklist/group/relation/page/query", description = "分页查询组成员")
    HttpResponse getBlackGroupMember(@Param("groupId") String groupId);

    @Post(path = "/api/v1/customer/blacklist/group/relation/page/query", description = "按手机号查询查询组成员")
    HttpResponse getBlackGroupMember(@Param("groupId") String groupId,
                                     @Param("phone") String phone);

    @Delete(path = "/api/v1/customer/blacklist/group/relation", description = "删除组成员")
    HttpResponse delBlackGroupMember(@Param("relationId") String relationId);

    @Get(path = "/api/v1/customer/blacklist/group/havingdata", description = "获取客户黑名单组")
    HttpResponse getBlacklistGroup();
    @Post(path = "/api/v1/customer/blacklist/group", description = "添加黑名单组")
    HttpResponse addBlackGroup(@Raw("$.name") String name);

    @Post(path = "/api/v1/customer/blacklist/group/relation", description = "添加组成员")
    HttpResponse addBlackGroupMember(@Raw("$.groupIds.[0]") String groupId,
                                     @Raw("$.memberDTOs.[0].customerName") String customerName,
                                     @Raw("$.memberDTOs.[0].phone") String phone);

}
