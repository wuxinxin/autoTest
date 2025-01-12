package com.example.dztest.service.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "sipp", url = "10.99.74.171:8181")
public interface SippFeignService {
    /**
     * 执行sipp号码注册
     */
    @GetMapping("/sipp/outbound/register")
    public boolean sippOutboundRegister();

    /**
     * 执行sipp号码呼入，异步返回
     */
    @GetMapping("/sipp/inbound")
    public void sippInbound(@RequestParam(value = "inBoundPhome", required = false) String inBoundPhome);

}
