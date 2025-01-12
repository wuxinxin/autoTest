package com.example.dztest.service.interfaces.uss;


import com.example.dztest.common.annotations.Get;
import com.example.dztest.common.annotations.Mapping;
import com.example.dztest.common.annotations.Param;
import com.example.dztest.domain.HttpResponse;

/*
* 统一技能组
 * */
@Mapping(path = "/uss", url = "${url.uss}")
public interface UssGroupApi {

    @Get(path = "/api/v1/ussgroup/staffs", description = "查询统一技能组坐席列表")
    HttpResponse getUssGroupStaffs(@Param("groupId") String groupId,
                                   @Param("searchInfo") String searchInfo);
}
