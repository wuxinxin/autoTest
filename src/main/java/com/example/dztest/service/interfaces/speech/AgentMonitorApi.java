package com.example.dztest.service.interfaces.speech;

import com.example.dztest.common.annotations.Get;
import com.example.dztest.common.annotations.Mapping;
import com.example.dztest.common.annotations.Param;
import com.example.dztest.common.annotations.Put;
import com.example.dztest.domain.HttpResponse;

@Mapping(path = "/speech", url = "${url.speech}")
public interface AgentMonitorApi {

    @Put(path = "/api/v1/agentMonitor/changePresence", description = "强制变更坐席状态")
    HttpResponse changePresence(@Param("agentId") Integer agentId ,
                                @Param("presence") String presence );

    @Get(path = "/api/v1/agentMonitor/monitor", description = "查询监控详情")
    HttpResponse getAgentMonitor(@Param("skillsetNames") String skillsetNames,
                                 @Param("staffIds") Integer staffIds,
                                 @Param("loginStatus") String loginStatus);
}
