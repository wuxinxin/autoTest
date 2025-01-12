package com.example.dztest.service.interfaces.outbound;

import com.example.dztest.common.annotations.Mapping;
import com.example.dztest.common.annotations.Post;
import com.example.dztest.common.annotations.Raw;
import com.example.dztest.domain.HttpResponse;

@Mapping(path = "/outbound")
public interface AudioRecordController {

    /*
     * 外呼通话记录已完成列表(智能语音-通话记录-外呼记录中调用)
     * */
    @Post(path = "/api/v1/audio/record/listPage", description = "外呼通话记录已完成列表")
    HttpResponse getRecordListPage(@Raw("$.leftCallStartTime") String leftCallStartTime,
                                   @Raw("$.rightCallStartTime") String rightCallStartTime );

    /*
     * 外呼通话记录已完成列表(智能呼入-通话记录)
     * */
    @Post(path = "/api/v1/audio/record/listPageIn", description = "呼入通话记录")
    HttpResponse getRecordListPageIn(@Raw("$.leftCallStartTime") String leftCallStartTime,
                                   @Raw("$.rightCallStartTime") String rightCallStartTime );

}
