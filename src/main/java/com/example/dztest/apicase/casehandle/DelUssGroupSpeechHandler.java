package com.example.dztest.apicase.casehandle;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.dztest.apicase.CaseHandler;
import com.example.dztest.apicase.common.GlobalApiVar;
import com.example.dztest.apicase.utils.BeanContextUtils;
import com.example.dztest.domain.HttpResponse;
import com.example.dztest.service.interfaces.uss.UssApi;
import org.testng.Assert;

/**
 * @author xinxin.wu
 * @description: 呼叫中心-删除技能组，用例处理器
 * @date 2023/10/18
 * @version: 1.0
 */
public class DelUssGroupSpeechHandler extends CaseHandler {

    /**
     * 手动注入spring
     */
    private final UssApi ussApi = BeanContextUtils.getBean(UssApi.class);

    /**
     * 前置处理，用于准备case数据等操作
     */
    @Override
    public void beforeHandle() {
    }

    @Override
    public void afterHandle() {
        System.out.println("delUssgroupSpeechHandler.beforeHandle");

        //打印excel中定义的code用户变量值
        logger.info("code is " + GlobalApiVar.userVars.get("code"));

    }

    @Override
    public void assertion(JSONObject body) {
        System.out.println("delUssgroupSpeechHandler.assertion");

        //获取技能组,判断是否删除成功
        HttpResponse httpResponseGet = ussApi.getUssgroups();
        JSONObject body1 = JSONObject.parseObject(httpResponseGet.getBody());

        Assert.assertEquals(body1.get("code"), "10000");
        JSONArray data = (JSONArray) body1.get("data");

        for (Object datum : data) {
            JSONObject skillGroup = (JSONObject) datum;
            if (skillGroup.get("group_id").toString().equals(GlobalApiVar.userVars.get("jvm.groupId"))) {
                Assert.fail();
                break;
            }
        }
    }

}
