package com.example.dztest.service.interfaces.outbound;

import com.example.dztest.common.annotations.*;
import com.example.dztest.domain.HttpResponse;

@Mapping(path = "/outbound")
public interface OutboundNoGroupsControllerApi {

    /*新外呼-获取外显号码*/
    @Get(path = "/api/v1/audio/outbound/group/list", description = "获取外显号码")
    HttpResponse getGroupList();

}
