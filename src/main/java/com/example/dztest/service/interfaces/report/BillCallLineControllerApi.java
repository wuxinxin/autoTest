package com.example.dztest.service.interfaces.report;

import com.example.dztest.common.annotations.Get;
import com.example.dztest.common.annotations.Mapping;
import com.example.dztest.common.annotations.Param;
import com.example.dztest.domain.HttpResponse;

/**
 *线路账单报表
 */
@Mapping(path = "/report", url = "${url.basic}")
public interface BillCallLineControllerApi {

    @Get(path = "/api/v1/bill/callline/detail/getList", description = "【B/A端】账单明细列表接口")
    HttpResponse getBillDetail(@Param("startDay") String startDay,
                               @Param("endDay") String endDay);

}
