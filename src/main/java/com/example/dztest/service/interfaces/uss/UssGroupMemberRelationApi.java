package com.example.dztest.service.interfaces.uss;

import com.example.dztest.common.annotations.*;
import com.example.dztest.domain.HttpResponse;
import com.example.dztest.utils.uilogger.UILogger;

/*
 * 统一技能组与成员关系
 * */
@Mapping(path = "/uss", url = "${url.uss}")
public interface UssGroupMemberRelationApi {
    @Get(path = "/api/v1/ussgroupmemberrelation", description = "获取统一技能组组内成员")
    HttpResponse getUssGroupMember(@Param("groupId") String groupId);

    @UILogger(desc = "新增统一技能组成员")
    @Post(path = "/api/v1/ussgroupmemberrelation", description = "新增统一技能组成员")
    HttpResponse addUssGroupMember(@Param("groupId") String groupId,
                                   @Param("userIds") String userIds);

    @Delete(path = "/api/v1/ussgroupmemberrelation", description = "删除统一技能组成员")
    HttpResponse delUssGroupMember(@Param("groupId") String groupId,
                                   @Param("userIds") String userIds);

    @Put(path = "/api/v1/ussmemberatt", description = "设置接待上线")
    HttpResponse setOnlineUpperLimit(
            @Param("groupId") String groupId,
            @Param("onlineUpperLimit") Integer onlineUpperLimit,
            @Param("onlinePriority") String onlinePriority,
            @Param("speechSkillValue") String speechSkillValue,
            @Param("userIds") String userIds
    );

}
