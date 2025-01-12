package com.example.dztest.service.interfaces.speech;

import com.example.dztest.common.annotations.Get;
import com.example.dztest.common.annotations.Mapping;
import com.example.dztest.common.annotations.Param;
import com.example.dztest.domain.HttpResponse;

@Mapping(path = "/speech", url = "${url.speech}")
public interface UserCommunicateApi {
    /**
     *
     * @param startDate
     * @param endDate
     * @param callType 全部=''；呼入=1；呼出=2；自动外呼=3
     * @return
     */

    @Get(path = "/api/v1/communicate/tenant/page", description = "查询用户信息")
    HttpResponse getCommunicate(@Param("startDate") String startDate,
                                @Param("endDate") String endDate,
                                @Param("callType") Integer callType);

    @Get(path = "/api/v1/communicate/tenant/page", description = "查询用户信息")
    HttpResponse getCommunicateByName(@Param("startDate") String startDate,
                                @Param("endDate") String endDate,
                                @Param("name") String name,
                                @Param("callType") Integer callType);

}
