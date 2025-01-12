package com.example.dztest.service.api_action.speech;

import com.alibaba.fastjson.JSONArray;
import com.example.dztest.common.annotations.MValue;
import com.example.dztest.domain.HttpResponse;
import com.example.dztest.service.interfaces.report.CallMonitor;
import com.example.dztest.service.interfaces.speech.AgentMonitorApi;
import com.example.dztest.utils.AssertUtil;
import com.jayway.jsonpath.JsonPath;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.testng.Assert;

@Service
public class AAgentMonitorApi {
    @Autowired
    private AgentMonitorApi agentMonitorApi;

    @MValue("speech.account2")
    private String account;

    @Autowired
    CallMonitor callMonitor;

    //获取坐席状态
    public String getAgentStatus(String skillsetNames, Integer staffIds, String agentName) {
        HttpResponse httpResponse = callMonitor.getAgentMonitor(skillsetNames, staffIds, "");
        JSONArray jsonArray1 = (JSONArray) AssertUtil.getData(httpResponse, "$.data.agentMonitorDetailList");

        String[] split = agentName.split("@");
        String key = split[0];

        String agentStatus = this.returnTheValueOfAKey("agentName", key, jsonArray1, "agentStatus");

        return agentStatus;
    }

    private String returnTheValueOfAKey(String key, String expect, JSONArray jsonArray, String retKey) {

        String ret = "";

        for (int i = 0; i < jsonArray.size(); i++) {
            //存在有的key值为null不判断
            if (jsonArray.getJSONObject(i).get(key) == null) {
                continue;
            }
            if (jsonArray.getJSONObject(i).get(key).equals(expect)) {
                ret = jsonArray.getJSONObject(i).get(retKey).toString();
                break;
            }
        }
        return ret;
    }

}
