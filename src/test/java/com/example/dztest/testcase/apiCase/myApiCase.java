package com.example.dztest.testcase.apiCase;

import com.example.dztest.apicase.CaseHandler;
import com.example.dztest.apicase.common.CaseExcelUtil;
import com.example.dztest.apicase.common.GlobalApiVar;
import com.example.dztest.apicase.common.GlobalExcelVar;
import com.example.dztest.apicase.data.TestDataProvider;
import com.example.dztest.service.api_action.ABasicApi;
import com.example.dztest.service.api_action.admin.AAdminApi;
import io.qameta.allure.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.Reporter;
import org.testng.annotations.*;

import static com.example.dztest.apicase.common.FunctionUtil.setFunctionsMap;
import static com.example.dztest.apicase.data.TestDataProvider.setCaseHandlers;

/**
 * @author xinxin.wu
 * @description: 接口用例执行
 * @date 2023/10/17
 * @version: 1.0
 */
@SpringBootTest
public class myApiCase extends AbstractTestNGSpringContextTests {
    @Autowired
    private ABasicApi aBasicApi;

    @Autowired
    private AAdminApi aAdminApi;

    /**
     * 上一条用例名称
     */
    private String last_case_name;

    /**
     * 现在用例名称
     */
    private String now_case_name;

//    /**
//     * 上一条用所属测试套
//     */
//    private String last_suite_name;

//    /**
//     * 现在用例所属测试套
//     */
//    private String now_suite_name;

    /**
     * @description: 处理整个Test运行前的初始化操作，此处初始化handlers、case以及初始变量
     * @param
     * @return void
     */
    @BeforeTest
    public void before() {
        //获取反射类，保存到集合
        setCaseHandlers();
        //获取内置函数，保存到集合
        setFunctionsMap();
    }


    @BeforeClass
    public void beforeClass() {
        //B端登录
        aBasicApi.login();
        //A端登录
//        aAdminApi.login();

        //读取excel，设置全局变量 GlobalVar.globalVars
        GlobalExcelVar.setVarFromExcel();
    }

    /**
     * 每个case运行前的前置条件、变量及数据处理
     */
    @BeforeMethod
    public void beforeMethod(Object[] handlers) {
        System.out.println("myApiCase.beforeMethod");
        CaseHandler caseHandler = (CaseHandler) handlers[0];

        this.now_case_name = caseHandler.getApiCaseModel().getCaseName();
        //当进入下一个测试套时，清空用户变量
        if (this.now_case_name == null || !this.now_case_name.equals(this.last_case_name)) {
            GlobalApiVar.userVars.clear();
        }

        //执行用户自定义beforeHandle
        caseHandler.beforeHandle();

        //case中的变量设置
        caseHandler.setUserVars();

        //初始httprequest请求数据
        caseHandler.initHttpRequest();
    }

    /**
     * 每个case运行后的变量及数据处理
     */
    @AfterMethod
    public void afterMethod(Object[] handlers) {
        System.out.println("myApiCase.afterMethod");
        CaseHandler caseHandler = (CaseHandler) handlers[0];

        this.last_case_name = caseHandler.getApiCaseModel().getCaseName();

        // “json提取”栏字符串设置用户变量
//        String jsonObtain = caseHandler.getApiCaseModel().getJsonObtain();
        String jsonObtain = CaseExcelUtil.buildParam(caseHandler.getApiCaseModel().getJsonObtain());
//        String json = "{\"code\":\"10000\",\"message\":\"请求成功\",\"data\":0}";
        //获取提取的变量名和变量值，并写入全局变量userVars
        logger.info("json提取结果为：");
        CaseExcelUtil.saveResult(caseHandler.getBodyRespone(), jsonObtain);

        //执行用户自定义afterHandle
        caseHandler.afterHandle();
    }

    /**
     * threadPoolSize = 10, timeOut = 3000
     * invocationCount: 用来设置用例被重复调用的次数
     * invocationCount = 10, threadPoolSize = 10, timeOut = 3000
     *
     * @param caseHandler case执行处理器
     */
    @Test(dataProvider = "apiCase", dataProviderClass = TestDataProvider.class, threadPoolSize = 1)
    public void autoTest(CaseHandler caseHandler) {
        Allure.epic(caseHandler.getApiCaseModel().getEpic());
        Allure.feature(caseHandler.getApiCaseModel().getFeature());
        Allure.story(caseHandler.getApiCaseModel().getCaseName());

        //log.info("[开始执行] case >>> {}", caseHandler.caseModel());
        Reporter.log("【执行请求】");
        caseHandler.execute();
        String title = caseHandler.apiCaseModel.getCaseName() +"|"+ caseHandler.apiCaseModel.getSteps();
        Allure.getLifecycle().updateTestCase(testResult -> testResult.setName(title));
        if (caseHandler.httpRequest != null) {
            Allure.step("接口请求信息：\n" + caseHandler.httpRequest.toString());
            Allure.step("接口返回信息：\n" + caseHandler.bodyRespone.toString());
        }
        Allure.description(caseHandler.apiCaseModel.getSteps());
        Reporter.log("【断言】");
        caseHandler.assertion();
    }

}
