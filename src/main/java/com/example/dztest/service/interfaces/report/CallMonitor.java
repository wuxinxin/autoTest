package com.example.dztest.service.interfaces.report;


import com.example.dztest.common.annotations.Get;
import com.example.dztest.common.annotations.Mapping;
import com.example.dztest.common.annotations.Param;
import com.example.dztest.domain.HttpResponse;

@Mapping(path = "/report", url = "${url.report}")
public interface CallMonitor {
    @Get(path = "/api/v1/call/callStatistics/callMonitor", description = "查询监控详情")
    HttpResponse getAgentMonitor(@Param("skillsetNames") String skillsetNames,
                                 @Param("staffIds") Integer staffIds,
                                 @Param("loginStatus") String loginStatus);

}
