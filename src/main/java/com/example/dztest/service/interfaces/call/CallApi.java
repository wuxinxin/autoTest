package com.example.dztest.service.interfaces.call;


import com.example.dztest.common.annotations.*;
import com.example.dztest.domain.HttpResponse;
import com.example.dztest.utils.uilogger.UILogger;

import java.util.List;

@Mapping(path = "/call")
public interface CallApi {


    @Get(path = "/api/v1/admin/outbound/numberGroups", description = "查询外呼号码组信息")
    HttpResponse getNumberGroups(
            @Param("LOCAL_TOKEN") String LOCAL_TOKEN
    );

    @Get(path = "/api/v1/call/record/queryDataOfTenantId", description = "查询新报表通话记录")
    HttpResponse queryDataOfTenantId(
            @Param("customerName") String customerName,
            @Param("callType") String callType,
            @Param("startDate") String startDate,
            @Param("endDate") String endDate,
            @Param("queryType") String queryType
    );

    @Get(path = "/api/v1/call/record/queryDataOfTenantId", description = "查询新报表通话记录")
    HttpResponse queryDataOfTenantIdIn(
            @Param("skillsetId") String skillsetId,
            @Param("callType") String callType,
            @Param("startDate") String startDate,
            @Param("endDate") String endDate,
            @Param("queryType") String queryType
    );


}
