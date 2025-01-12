package com.example.dztest.service.interfaces.basic;

import com.example.dztest.common.annotations.*;
import com.example.dztest.domain.HttpResponse;

/*
 * 工单标签服务
 * */
@Mapping(path="/basic", url = "${url.basic}")
public interface BasicWorkOrderTagApi {

    @Post(path = "/api/v1/tagGroup/tag/create", description = "创建工单标签")
    HttpResponse createWorkOrderTag(
            @Raw("tagGroupId") String tagGroupId,
            @Raw("tagName") String tagName
    );

    @Post(path = "/api/v1/tagGroup/tag/delete", description = "删除工单标签")
    HttpResponse deleteWorkOrderTag(
            @Raw("tagGroupId") String tagGroupId,
            @Raw("tagId") String tagId
    );

}
