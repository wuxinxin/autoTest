package com.example.dztest.service.feign;


import com.alibaba.fastjson.JSONObject;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "tee", url = "10.193.180.138:8080")
public interface SpeechFeignService {
    /**
     * 查询监控详情
     *
     * @param tenantId  公共header参数，租户ID
     */
    @GetMapping("/api/v1/agentMonitor/monitor")
    JSONObject getTenant(@RequestParam(value = "tenantId", required = false) String tenantId,
                         @RequestParam(value = "userId", required = false) Long userId,
                         @RequestParam(value = "staffIds", required = false) Long[] staffIds);

}
