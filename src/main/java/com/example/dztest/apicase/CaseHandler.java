package com.example.dztest.apicase;

import com.alibaba.fastjson.JSONObject;
import com.example.dztest.apicase.common.CaseExcelUtil;
import com.example.dztest.apicase.common.GlobalApiVar;
import com.example.dztest.apicase.model.ApiCaseModel;
import com.example.dztest.apicase.utils.BeanContextUtils;
import com.example.dztest.domain.GlobalVar;
import com.example.dztest.domain.HttpRequest;
import com.example.dztest.domain.HttpResponse;
import com.example.dztest.domain.HttpType;
import com.example.dztest.service.api_action.admin.AAdminApi;
import com.example.dztest.utils.HttpUtil;
import com.example.dztest.utils.ProxyUtils;
import com.jayway.jsonpath.JsonPath;
import io.qameta.allure.Allure;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.asserts.SoftAssert;

import java.math.BigDecimal;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author xinxin.wu
 * @description: case用例父类，用例数据处理、用例执行、用例断言
 * @date 2023/10/11
 * @version: 1.0
 */
public class CaseHandler {

    protected final Logger logger = LoggerFactory.getLogger(this.getClass());
    /**
     * 单条用例数据对应实体
     */
    public ApiCaseModel apiCaseModel;
    /**
     * http返回实体
     */
    public HttpResponse httpResponse;
    /**
     * 返回body
     */
    public JSONObject bodyRespone;
    /**
     * http请求实体
     */
    public HttpRequest httpRequest;
    /**
     * 软断言对象
     */
    public SoftAssert softAssert;

    /**
     * 手动获取bean实例
     */
    private AAdminApi aAdminApi = BeanContextUtils.getBean(AAdminApi.class);


    /**
     * @description: 处理“变量设置”栏数据，替换变量和函数，并添加用户变量
     * @param
     * @return void
     */
    public void setUserVars() {
        //”变量设置“栏字符串初始处理：1.替换变量和函数 2.根据设置添加用户变量 3.执行函数
        CaseExcelUtil.setUserVars(this.apiCaseModel.getExecute());
    }

    /**
     * @description: 根据单条用例数据实体，设置http请求数据
     * @param
     * @return void
     */
    public void initHttpRequest() {
        if (this.apiCaseModel.getHttpMethod() == null || this.apiCaseModel.getHttpMethod().isEmpty()) {
            logger.error("请求方式为空");
            return;
        }
        this.httpRequest = new HttpRequest();
        this.softAssert = new SoftAssert();

        //设置请求方式
        switch (this.apiCaseModel.getHttpMethod()) {
            case "post":
                this.httpRequest.setType(HttpType.POST);
                break;
            case "get":
                this.httpRequest.setType(HttpType.GET);
                break;
            case "put":
                this.httpRequest.setType(HttpType.PUT);
                break;
            case "delete":
                this.httpRequest.setType(HttpType.DELETE);
                break;
            default:
                logger.error("不能匹配请求方式post/get/delete/put");
        }

        //获取原始请求入参
        String param = this.apiCaseModel.getParams();
        //替换变量和函数
        String param_p = CaseExcelUtil.buildParam(param);
        //设置body 或 param
        CaseExcelUtil.setParams(param_p, this.httpRequest);

        //设置path和param
        String path_p = CaseExcelUtil.buildParam(this.apiCaseModel.getPath());
        CaseExcelUtil.setPath(path_p, this.httpRequest);

        //设置server
        if ("a端".equals(this.apiCaseModel.getModule())) {
            httpRequest.setServer_url(ProxyUtils.A_SERVER_URL);
        } else {
            httpRequest.setServer_url(ProxyUtils.server_url);
        }

        //设置headers
//        CaseExcelUtil.setHeaders(this.apiCaseModel.getModule(), this.apiCaseModel.getHeaders(), httpRequest);
        //获取请求头
        String headers = this.apiCaseModel.getHeaders();
        //替换变量和函数
        String headers_p = CaseExcelUtil.buildParam(headers);
        this.setHeaders(headers_p);
    }

    /**
     * @description: 设置请求头信息
     * @param headersStr  用例文件定义的头字符串
     * @return void
     */
    private void setHeaders(String headersStr) {
        String module = this.apiCaseModel.getModule();
        //如果Authorization不为空，说明登录过
        if ("b端".equals(module) && GlobalVar.GLOBAL_DATA_MAP.get("Authorization") != null) {
            this.httpRequest.getHeaders().put("Authorization", GlobalVar.GLOBAL_DATA_MAP.get("Authorization"));
        } else if ("a端".equals(module)) {
            //没有a端登录，则登录
            if (GlobalVar.A_GLOBAL_DATA_MAP.get("Authorization") == null) {
                aAdminApi.login();
            }
            this.httpRequest.getHeaders().put("Authorization", GlobalVar.A_GLOBAL_DATA_MAP.get("Authorization"));
        } else if ("openapi".equals(module)) {
            String appId = GlobalApiVar.globalVars.get("g.openapi.appId");
            String sign = GlobalApiVar.globalVars.get("g.openapi.sign");
            String timestamp = GlobalApiVar.globalVars.get("g.openapi.timestamp");
            this.httpRequest.getHeaders().put("appId", appId);
            this.httpRequest.getHeaders().put("sign", sign);
            this.httpRequest.getHeaders().put("timestamp", timestamp);
        } else {
            System.out.println("未匹配模块");
        }

        //如果不是文件上传接口，Content-Type默认为application/json
        if (this.httpRequest.getFiles().size() == 0) {
            this.httpRequest.getHeaders().put("Content-Type", "application/json");
        }

        if (null == headersStr || "".equals(headersStr)) {
            return;
        }

        Pattern pattern = Pattern.compile("([^;=]*)=([^;]*)");
        Matcher m = pattern.matcher(headersStr.trim());

        //phone=18191992233;empno=990033&id=
        //利用;将字符串分隔开来
        String[] headers = headersStr.split(";");

        //遍历save  msg_id=$.jsonPath.xx；
        for (String header : headers) {
            //	正则表达式 由0-n个非；=组成的字符串 =  0-n个非;组成的字符串
            //对msg_id=ssss做正则匹配
            Matcher matcher = pattern.matcher(header.trim());
            while (matcher.find()) {
                String key = matcher.group(1);
                String value = matcher.group(2);

                //将key value加到headers
                httpRequest.getHeaders().put(key, value);
            }
        }
    }

    /**
     * @description: 前置处理，用于准备case数据等操作
     * @param
     * @return void
     */
    public void beforeHandle() {
        System.out.println("CaseHandler.beforeHandle");
    }

    /**
     * @description: 后置处理，用于还原case执行后的数据等操作
     * @param
     * @return void
     */
    public void afterHandle() {
        System.out.println("CaseHandler.afterHandle");
    }

    /**
     * @description: 自定义断言，用于扩展断言功能
     * @param bodyRespone   接口返回数据的JSONObject结果
     * @return void
     */
    public void assertion(JSONObject bodyRespone) {
        Reporter.log("【处理器】【自定义断言】");
    }

    /**
     * @description: case执行主体，用于执行测试
     * @param
     * @return void
     */
    public void execute() {
        System.out.println("CaseHandler.execute");
        try {
            Reporter.log("接口请求信息：" + httpRequest.toString());
            this.httpResponse = HttpUtil.getResponse("", httpRequest);
            Reporter.log("接口返回信息：" + httpResponse.toString());
            this.bodyRespone = JSONObject.parseObject(this.httpResponse.getBody());
        } catch (Exception e) {
            logger.error("接口返回信息异常，请检查接口组装是否正确");
            logger.error(e.toString());
        }
    }

    /**
     * @description: case断言
     * @param
     * @return void
     */
    public final void assertion() {
        //excel中响应断言
        Map<String, Map<String, String>> assertMap = CaseExcelUtil.getAssertMap(this.apiCaseModel.getAssertion());
        if (assertMap == null) {
            return;
        }

        assertMap.forEach((keyS, valueMap) -> {
//            String ope = "=";
//            if ("!=".equals(keyS)) {
//                ope = "!=";
//            }

            String finalOpe = keyS;
            valueMap.forEach((key, value) -> {
                Object actual_value_o = JsonPath.read(this.bodyRespone, key);
                String actual_value = "";
                //object都转为string类型
                if (actual_value_o instanceof BigDecimal) {
                    actual_value = ((BigDecimal) actual_value_o).toPlainString();
                } else {
                    //其他类型如Boolen，直接转为string
                    actual_value = actual_value_o.toString();
                }
                String expect_value = CaseExcelUtil.buildParam(value);
                Reporter.log(key + " 期望值:" + expect_value + "；实际值：" + actual_value, true);
                if ("=".equals(finalOpe)) {
                    Allure.step("断言：\n" + key + " 期望值:" + expect_value + "等于 实际值：" + actual_value);
                    Assert.assertEquals(actual_value, expect_value);
                } else if ("!=".equals(finalOpe)) {
                    Allure.step("断言：\n" + key + " 期望值:" + expect_value + "不等于 实际值：" + actual_value);
                    Assert.assertNotEquals(actual_value, expect_value);
                } else if ("=~".equals(finalOpe)) {
                    Allure.step("断言：\n" + key + " 期望值:" + expect_value + "约等于 实际值：" + actual_value);
                    Assert.assertTrue(actual_value.matches(expect_value));
                } else {
                    Reporter.log("不期待的断言方式");
                }

            });
        });


        //casehandler中自定义断言
        assertion(this.bodyRespone);

        this.softAssert.assertAll();
    }

    public ApiCaseModel getApiCaseModel() {
        return apiCaseModel;
    }

    public void setApiCaseModel(ApiCaseModel apiCaseModel) {
        this.apiCaseModel = apiCaseModel;
    }

    public HttpRequest getHttpRequest() {
        return httpRequest;
    }

    public void setHttpRequest(HttpRequest httpRequest) {
        this.httpRequest = httpRequest;
    }

    public JSONObject getBodyRespone() {
        return bodyRespone;
    }

    public void setBody(JSONObject body) {
        this.bodyRespone = body;
    }
}
