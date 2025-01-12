package com.example.dztest.service.interfaces.imbasic;


import com.example.dztest.common.annotations.Get;
import com.example.dztest.common.annotations.Mapping;
import com.example.dztest.common.annotations.Param;
import com.example.dztest.common.annotations.Post;
import com.example.dztest.domain.HttpResponse;

/*
 *
 * */
@Mapping(path="/imbasic")
public interface GroupApi {

        @Post(path = "/api/v1/group/list/all", description = "获取在线客服技能组")
        HttpResponse getImGroupList();

}
