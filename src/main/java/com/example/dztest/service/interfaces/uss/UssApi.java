package com.example.dztest.service.interfaces.uss;

import com.example.dztest.common.annotations.*;
import com.example.dztest.domain.HttpResponse;
import com.example.dztest.utils.uilogger.UILogger;


@Mapping(path = "/uss", url = "${url.uss}")
public interface UssApi {

    @UILogger(desc = "新增呼叫中心技能组")
    @Post(path = "/api/v1/ussgroup", description = "新增呼叫中心技能组")
    HttpResponse addUssgroup(@Raw("$.groupName") String groupName);

    @Post(path = "/api/v1/ussgroup", description = "新增呼叫中心技能组")
    HttpResponse addUssgroup(@Raw("$.groupName") String groupName,
                             @Raw("$.speechDid") String speechDid,
                             @Raw("$.speechType") Integer speechType);

    @UILogger(desc = "新增技能组")
    @Post(path = "/api/v1/ussgroup", description = "新增技能组")
    HttpResponse addUssgroup(@Raw("$.groupName") String groupName,
                             @tag("tag") String tag);

    @UILogger(desc = "新增呼叫中心技能组/api/v1/{ussgroup}")
    @Post(path = "/api/v1/{ussgroup}", description = "新增呼叫中心技能组")
    HttpResponse addUssgroup(@Header("sign") String sign,
                             @PathVariable("ussgroup") String ussgroup,
                             @Param("tenantId") String tenantId,
                             @Raw( "$.groupName") String groupName,
                             @Raw("$.speechAcw") int speechAcw,
                             @tag("tag") String tag);

    @UILogger(desc = "更新技能组/api/v1/ussgroup")
    @Put(path = "/api/v1/ussgroup", description = "更新技能组")
    HttpResponse putUssgroup(@Raw("$.groupName") String groupName,
                             @Param("groupId") String groupId,
                             @Raw("$.speechDid") String speechDid);

    @UILogger(desc = "更新技能组/api/v1/ussgroup")
    @Put(path = "/api/v1/ussgroup", description = "更新技能组")
    HttpResponse putUssgroup(@Param("id") String id,
                             @Param("groupId") String groupId,
                             @Raw("$.groupName") String groupName,
                             @Raw("id") String id_1,
                             @Raw("$.speechDid") String speechDid);


    @Put(path = "/api/v1/ussgroup", description = "更新技能组")
    HttpResponse putUssgroup(@Raw("$.groupName") String groupName,
                             @Raw("$.id") Long raw_id,
                             @Param("groupId") String groupId,
                             @Param("id") Long id,
                             @tag("tag") String tag);

    @Get(path = "/api/v1/ussgroup", description = "获取呼叫中心技能组列表")
    HttpResponse getUssgroups();

    @Delete(path = "/api/v1/ussgroup", description = "删除呼叫中心技能组")
    HttpResponse delUssGroup(@Param("groupId") String groupId);

    @Get(path = "/api/v1/ussgroupmemberrelation", description = "获取呼叫中心技能组成员关系")
    HttpResponse ussgroupmemberrelation(@Param("groupId") String groupId);
}
