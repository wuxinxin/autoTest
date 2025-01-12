package com.example.dztest.service.interfaces.imbasic;

import com.example.dztest.common.annotations.Mapping;
import com.example.dztest.common.annotations.Param;
import com.example.dztest.common.annotations.Post;
import com.example.dztest.common.annotations.Raw;
import com.example.dztest.domain.HttpResponse;

@Mapping(path="/imbasic")
public interface KeywordApi {
    @Post(path = "/api/v1/staffQuickReply/queryByKeyword",description = "获取个人快捷语")
    HttpResponse getPersonalKeywordList();

    @Post(path = "/api/v1/staffQuickReply/saveOrUpdate",description = "添加个人快捷语")
    HttpResponse addPersonalKeyword(@Raw("$.keyword") String keyword,
                                    @Raw("$.replyDetail") String replyDetail
    );
}
