package com.example.dztest.service.api_action.basic;

import com.alibaba.fastjson.JSONArray;
import com.example.dztest.domain.HttpResponse;
import com.example.dztest.service.interfaces.basic.SessionDistributeStrategyApi;
import com.example.dztest.utils.AssertUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ASessionDistributeStrategyApi {

    @Autowired
    private SessionDistributeStrategyApi sessionDistributeStrategyApi;

    /*
    * 通话策略名获取策略id
    * @param strategyName 策略名字
    * */
    public Integer getStrategyID(String strategyName) {
        HttpResponse httpResponse = sessionDistributeStrategyApi.getStrategy();
        JSONArray jsonArray = (JSONArray) AssertUtil.getData(httpResponse, "$.data");
        for (int i = 0; i < jsonArray.size(); i++) {
            if (strategyName.equals(jsonArray.getJSONObject(i).getString("name"))) {
                return jsonArray.getJSONObject(i).getInteger("id");
            }
        }
        return null;
    }


}
