package com.example.dztest.controller;

import com.alibaba.fastjson.JSONObject;
import com.example.dztest.service.feign.SpeechFeignService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class speechapi {
    @Autowired
    private SpeechFeignService speechFeignService;

//    @Autowired
//    private Hello1 hello1;

    @GetMapping(value = "/speech/test")
    public JSONObject test(@RequestParam(value = "tenantId", required = false) String tenantId,
                           @RequestParam(value = "userId", required = false) Long userId,
                           @RequestParam(value = "staffIds", required = false) Long[] staffIds)
    {
        JSONObject aa = speechFeignService.getTenant(tenantId, userId, staffIds);
        return aa;
    }

//    @GetMapping(value = "/speech/test2")
//    public JSONObject test2()
//    {
//        hello1.morning("tettt");
//        return null;
//    }

}
