package com.example.dztest.apicase.casehandle;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.dztest.apicase.CaseHandler;
import com.example.dztest.apicase.common.GlobalApiVar;

/**
 * @author xinxin.wu
 * @description: 呼叫中心-查询技能组，用例处理器
 * @date 2023/10/18
 * @version: 1.0
 */
public class GetUssGroupSpeechHandler extends CaseHandler {

    /**
     * 前置处理，用于准备case数据等操作
     */
    @Override
    public void beforeHandle() {
        super.beforeHandle();
    }

    @Override
    public void afterHandle() {
        System.out.println("myCaseHandler.beforeHandle");

        //打印excel中定义的code用户变量值
        logger.info("code is " + GlobalApiVar.userVars.get("code"));

    }

    @Override
    public void assertion(JSONObject body) {
        System.out.println("myCaseHandler.assertion");

        //自定义断言，判断技能组列表中有用户变量"groupName"对应的技能组;并添加用户变量group_id、id
        boolean isExist = false;
        JSONArray data = (JSONArray) body.get("data");
        for (Object datum : data) {
            JSONObject skillGroup = (JSONObject) datum;
            if (skillGroup.get("group_name").toString().equals(GlobalApiVar.userVars.get("groupName"))) {
                //技能组id
                String groupId = skillGroup.get("group_id").toString();
                String id = skillGroup.get("id").toString();

                GlobalApiVar.userVars.put("jvm.groupId", groupId);
                GlobalApiVar.userVars.put("jvm.id", id);
                GlobalApiVar.globalVars.put("g.jvm.speech.groupId", groupId);
                isExist = true;
                break;
            }
        }
        this.softAssert.assertTrue(isExist);
    }
}
