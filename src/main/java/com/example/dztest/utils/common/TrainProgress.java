package com.example.dztest.utils.common;

import com.alibaba.fastjson.JSONArray;
import com.example.dztest.domain.HttpResponse;
import com.example.dztest.service.interfaces.nlu.NluApi;
import com.example.dztest.utils.AssertUtil;
import com.example.dztest.utils.common.DelayCall;
import com.example.dztest.utils.common.Delayer;
import org.springframework.stereotype.Component;

/**
 * 机器人进度检查类
 */

@Component
public class TrainProgress implements DelayCall<NluApi>, Delayer {
    @Override
    public HttpResponse executeUntil(NluApi func, Object expect, Integer delay, Object... o) {
        HttpResponse httpResponse;
        String robotId = o[0].toString();
        String modelIds = o[1].toString();
        String progress = "";

        long startTime = System.currentTimeMillis();
        while (true) {
            HttpResponse response = func.progress(robotId, modelIds);
            progress = AssertUtil.getData(response, "$.data.progress").toString();
            String status = AssertUtil.getData(response, "$.data.status").toString();

            //1-启动中 2-排队中 3-训练中 4-训练成功 5-训练失败 6-训练终止
            //训练进度为100或者训练状态为5/6,或者有原因都表示失败
            if (progress.equals("100") || status.equals("5") || status.equals("6")) {
                httpResponse = response;
                break;
            }


            try {
                Thread.sleep(delay);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            long endTime = System.currentTimeMillis();

            long takenTime = endTime - startTime;

            System.out.println("当前训练进度progress 为: " + progress + "%");
            System.out.println("当前训练耗时: " + takenTime / 1000 + "秒");

            //超过15分钟还没训练完当做失败
            if (takenTime >= 900000 ) {
                System.out.println("训练已超时，总共耗时: " + takenTime / 1000 + "秒");
                httpResponse = response;
                break;
            }
        }
        return httpResponse;
    }
}
