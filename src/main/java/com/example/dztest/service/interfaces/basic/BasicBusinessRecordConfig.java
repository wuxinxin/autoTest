package com.example.dztest.service.interfaces.basic;

import com.example.dztest.common.annotations.*;
import com.example.dztest.domain.HttpResponse;


@Mapping(path = "/basic", url = "${url.basic}")
public interface BasicBusinessRecordConfig {
    @Get(path = "/api/v1/chatSummary/tree", description = "公共配置/业务记录分类信息获取")
    HttpResponse tree();
    @Post(path = "/api/v1/chatSummary/save", description = "导入业务小结")
    HttpResponse addChatSummary(
            @Raw("$.summaries[0].summary") String summary
    );
    @Post(path = "/api/v1/chatSummary/save/increment/template", description = "导入业务小结")
    HttpResponse importChatSummary(
            @FilePath("template") String template
    );
}
