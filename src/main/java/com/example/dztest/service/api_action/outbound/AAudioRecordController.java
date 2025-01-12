package com.example.dztest.service.api_action.outbound;

import com.example.dztest.domain.HttpResponse;
import com.example.dztest.service.interfaces.outbound.AudioRecordController;
import com.example.dztest.utils.TimeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AAudioRecordController {

    @Autowired
    private AudioRecordController audioRecordController;

    /*
    * 查询当天通话数据（(智能语音-通话记录-外呼记录）
    * */
    public HttpResponse getTodayRecordList() {
        String leftCallStartTime = TimeUtil.getNowTime("yyyy-MM-dd 00:00:00");
        String rightCallStartTime = TimeUtil.getNowTime("yyyy-MM-dd 23:59:59");
        return audioRecordController.getRecordListPage(leftCallStartTime, rightCallStartTime);

    }
    /*

     * 查询当天通话数据（(智能呼入-通话记录）
     * */
    public HttpResponse getTodayRecordListIn() {
        String leftCallStartTime = TimeUtil.getNowTime("yyyy-MM-dd 00:00:00");
        String rightCallStartTime = TimeUtil.getNowTime("yyyy-MM-dd 23:59:59");
        return audioRecordController.getRecordListPageIn(leftCallStartTime, rightCallStartTime);

    }

}
