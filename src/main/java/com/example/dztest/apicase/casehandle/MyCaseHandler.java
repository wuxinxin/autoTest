package com.example.dztest.apicase.casehandle;

import com.alibaba.fastjson.JSONObject;
import com.example.dztest.apicase.CaseHandler;
import com.example.dztest.apicase.common.GlobalApiVar;
import org.testng.Assert;

/**
 * @author xinxin.wu
 * @description: case用例子类，处理器演示demo
 * @date 2023/10/12
 * @version: 1.0
 */
public class MyCaseHandler extends CaseHandler {

    /**
     * @description: 前置处理，用于准备case数据等操作
     */
    @Override
    public void beforeHandle() {
        super.beforeHandle();

        //前置处理逻辑
        System.out.println("myCaseHandler.beforeHandle");
        GlobalApiVar.userVars.put("test", "tttttt");
    }

    /**
     * @description: 后置处理，用于清理case数据等操作
     */
    @Override
    public void afterHandle() {
        super.afterHandle();

        //后置处理逻辑
        System.out.println("myCaseHandler.beforeHandle");
        //打印excel中定义的code用户变量值
        logger.info("code is " + GlobalApiVar.userVars.get("code"));
    }

    /**
     * @description: 自定义断言
     * @param body  http请求返回body
     */
    @Override
    public void assertion(JSONObject body) {

        //自定义断言逻辑处理
        System.out.println("myCaseHandler.assertion");
        Assert.assertTrue(true);
    }
}
