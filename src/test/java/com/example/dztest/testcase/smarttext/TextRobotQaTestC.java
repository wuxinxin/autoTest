package com.example.dztest.testcase.smarttext;

import com.alibaba.fastjson.JSONObject;
import com.example.dztest.common.annotations.MValue;
import com.example.dztest.domain.GlobalVar;
import com.example.dztest.domain.HttpResponse;
import com.example.dztest.service.BaseOfTestCase;
import com.example.dztest.service.api_action.basic.ASessionDistributeStrategyApi;
import com.example.dztest.service.interfaces.basic.BasicAuthControllerApi;
import com.example.dztest.service.interfaces.basic.BasicProductManagementApi;
import com.example.dztest.service.interfaces.basic.RobotTestApi;
import com.example.dztest.service.interfaces.basic.SessionDistributeStrategyApi;
import com.example.dztest.service.interfaces.chat.ChatApi;
import com.example.dztest.service.interfaces.engine.EngineApi;
import com.example.dztest.service.interfaces.kms.RobotApi;
import com.example.dztest.service.interfaces.nlu.NluApi;
import com.example.dztest.service.interfaces.outbound.OutboundApi;
import com.example.dztest.utils.AssertUtil;
import com.example.dztest.utils.TimeUtil;
import com.example.dztest.utils.common.RobotUtil;
import com.example.dztest.utils.common.TrainProgress;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import javax.validation.constraints.NotNull;

@SpringBootTest
@Epic("文本机器人")
@Feature("智能文本应答C端验证")
public class TextRobotQaTestC extends BaseOfTestCase {
    private static final Logger logger = LoggerFactory.getLogger(TextRobotQaTestC.class);
    @Value("${portal.url}")
    private String access_path;

    @Autowired
    RobotApi robotApi;

    @Autowired
    RobotUtil robotUtil;

    @Autowired
    NluApi nluApi;

    @Autowired
    TrainProgress trainProgress;

    @Autowired
    RobotTestApi robotTestApi;

    @Autowired
    OutboundApi outboundApi;

    @Autowired
    EngineApi engineApi;

    @Autowired
    ChatApi chatApi;

    @Autowired
    private SessionDistributeStrategyApi sessionDistributeStrategyApi;

    @Autowired
    private ASessionDistributeStrategyApi aSessionDistributeStrategyApi;

    @Autowired
    private BasicProductManagementApi basicProductManagementApi;

    @Autowired
    private BasicAuthControllerApi basicAuthControllerApi;


    //策略信息
    private Integer strategyID;
    private String productName;
    private String productID;
    private String chatToken;


    private String filepath;
    private String robotName;

    //机器人robotId
    @MValue("${imchat.smarttext_robotid2}")
    private String robotId;

    //机器人id
    private String id;

    private String LOCAL_TOKEN;
    private String tenantId;
    private String sessionId;
    private String callRecordId;
    private String flowCode;
    private String questionAuthorization;
    private String origin_auth;
    private HttpResponse httpResponse2;


    @BeforeClass
    @Parameters({"out_account", "out_pwd"})
    public void setUp(String out_account, String out_pwd) {
        aBasicApi.login();

        //filepath只需要给带后缀的文件名
//        this.filepath = "smarttext_robot_n.zip";
//
//        this.robotName = "auto" + robotUtil.getRandomDataByLen(6);
//
//        HttpResponse httpResponse2 = robotApi.robotAdd(this.robotName);
//        String taskId = AssertUtil.getData(httpResponse2, "$.data").toString();
//
//        //导入机器人
//        Map<String, String> map = robotUtil.importRobot(this.robotName, this.filepath, taskId);
//
//        this.robotId = map.get("robotId");
//        this.id = map.get("id");


        //训练机器人
//        this.trainRobot(this.robotId);


        //新建渠道信息
        this.productName = "渠道" + TimeUtil.getNowTime("yyyyMMdd-HHMMSS");

        //新建在线渠道
        HttpResponse httpResponse = basicProductManagementApi.addProduct(this.productName);
        AssertUtil.assertBodyEquals(httpResponse, "$.code", "10000");
        this.productID = (String) AssertUtil.getData(httpResponse, "$.data.productId");

        //获取c端chat token
        HttpResponse httpResponse6 = basicAuthControllerApi.getChatToken(this.productID);
        this.chatToken = (String) AssertUtil.getData(httpResponse6, "$.data");


        //新建在线会话策略
        String strategyName = "策略" + TimeUtil.getNowTime("yyyyMMddHHMMSS");
        HttpResponse httpResponse8 = sessionDistributeStrategyApi.addStrategyOnlyRobot(strategyName, this.productID, this.robotId);
        this.strategyID = aSessionDistributeStrategyApi.getStrategyID(strategyName);

        //31.4版本新增逻辑，新建的策略默认从开启变成关闭需要开启策略
        HttpResponse httpResponse22 = sessionDistributeStrategyApi.openCloseStrategy(this.strategyID, "1");
        AssertUtil.assertBodyEquals(httpResponse22, "$.message", "请求成功");

        //临时变更aut
        this.origin_auth = GlobalVar.GLOBAL_DATA_MAP.get("Authorization").toString();
        GlobalVar.GLOBAL_DATA_MAP.put("Authorization", this.chatToken);

        HttpResponse httpResponse1 = chatApi.initSession(this.chatToken);
        AssertUtil.assertBodyEquals(httpResponse1, "$.message", "调用成功");
        this.sessionId = AssertUtil.getData(httpResponse1, "$.data.sessionId").toString();
    }

    @AfterClass
    public void tearDown() {
        //删除机器人数据恢复
//        this.deleteRobot(this.id);

        //恢复auth
        GlobalVar.GLOBAL_DATA_MAP.put("Authorization", this.origin_auth);
    }

    @Test(priority = 0)
    @Story("问答知识应答testcase-view-2618")
    public void QaKnowledgeQa() {
        HttpResponse rsp = chatApi.answer("何时自由", this.chatToken, this.chatToken, this.sessionId, this.chatToken);
        AssertUtil.assertBodyEquals(rsp, "$.data.dmResSingleAnswerList[0].internalType", "REPLY_KNOWLEDGE");
        AssertUtil.assertBodyContains(rsp, "$.data.dmResSingleAnswerList[0].answerData", "随时都可以自由");
    }

    @Test(priority = 1)
    @Story("寒暄实体应答testcase-view-2619")
    public void HxEntityQa() {
        //开始验证
        HttpResponse httpResponse = chatApi.answer("心情如天气", this.chatToken, this.chatToken, this.sessionId, this.chatToken);
        AssertUtil.assertBodyEquals(httpResponse, "$.data.dmResSingleAnswerList[0].internalType", "REPLY_KNOWLEDGE");
        AssertUtil.assertBodyContains(httpResponse, "$.data.dmResSingleAnswerList[0].answerData", "心情不是很美丽12341324");
    }

    @Test(priority = 2)
    @Story("枚举实体应答testcase-view-2622")
    public void enumEntityQa() {
        //开始验证
        HttpResponse httpResponse = chatApi.answer("小米手机", this.chatToken, this.chatToken, this.sessionId, this.chatToken);
        AssertUtil.assertBodyEquals(httpResponse, "$.data.dmResSingleAnswerList[0].internalType", "TEXT");
        AssertUtil.assertBodyContains(httpResponse, "$.data.dmResSingleAnswerList[0].answerData", "我是枚举实体");
    }

    @Test(priority = 3)
    @Story("正则实体应答testcase-view-2623")
    public void expressQa() {
        //开始验证
        HttpResponse httpResponse = chatApi.answer("手机", this.chatToken, this.chatToken, this.sessionId, this.chatToken);
        AssertUtil.assertBodyEquals(httpResponse, "$.data.dmResSingleAnswerList[0].internalType", "TEXT");
        AssertUtil.assertBodyContains(httpResponse, "$.data.dmResSingleAnswerList[0].answerData", "我是正则实体");
    }

    @Test(priority = 4)
    @Story("属性应答testcase-view-2624")
    public void itemQa() {
        //开始验证
        HttpResponse httpResponse = chatApi.answer("尺寸", this.chatToken, this.chatToken, this.sessionId, this.chatToken);
        AssertUtil.assertBodyEquals(httpResponse, "$.data.dmResSingleAnswerList[0].internalType", "TEXT");
        AssertUtil.assertBodyContains(httpResponse, "$.data.dmResSingleAnswerList[0].answerData", "尺寸属性答案");
    }

    @Test(priority = 5)
    @Story("二维表应答testcase-view-2628")
    public void twoTableQa() {
        //开始验证
        HttpResponse httpResponse = chatApi.answer("内存", this.chatToken, this.chatToken, this.sessionId, this.chatToken);
        AssertUtil.assertBodyEquals(httpResponse, "$.data.dmResSingleAnswerList[0].internalType", "TEXT");
        AssertUtil.assertBodyContains(httpResponse, "$.data.dmResSingleAnswerList[0].answerData", "正则手机100g");
    }

    @Test(priority = 6)
    @Story("任务流程应答testcase-view-2630")
    public void taskProcessQa() {
        //开始验证 TODO
        //1.对话节点验证
        HttpResponse httpResponse = chatApi.answer("开始", this.chatToken, this.chatToken, this.sessionId, this.chatToken);
        AssertUtil.assertBodyEquals(httpResponse, "$.data.dmResSingleAnswerList[0].internalType", "TEXT");
        AssertUtil.assertBodyContains(httpResponse, "$.data.dmResSingleAnswerList[0].answerData", "欢迎进入对话节点");

        //2.跳转节点-退出分支
        HttpResponse httpResponse1 = chatApi.answer("不存在的问题", this.chatToken, this.chatToken, this.sessionId, this.chatToken);
        AssertUtil.assertBodyEquals(httpResponse1, "$.data.dmResSingleAnswerList[0].internalType", "TEXT");
        AssertUtil.assertBodyContains(httpResponse1, "$.data.dmResSingleAnswerList[0].answerData", "未知分支退出");

        //退出后需要重新初始化
        HttpResponse httpResponse11 = chatApi.initCSession(this.chatToken, this.chatToken);
        this.sessionId = AssertUtil.getData(httpResponse11, "$.data.sessionId").toString();
        AssertUtil.assertBodyEquals(httpResponse11, "$.message", "调用成功");

        //3.对话节点验证
        HttpResponse httpResponse2 = chatApi.answer("开始", this.chatToken, this.chatToken, this.sessionId, this.chatToken);
        AssertUtil.assertBodyEquals(httpResponse2, "$.data.dmResSingleAnswerList[0].internalType", "TEXT");
        AssertUtil.assertBodyContains(httpResponse2, "$.data.dmResSingleAnswerList[0].answerData", "欢迎进入对话节点");

    }

    /**
     * 训练机器人
     *
     * @param robotId
     */
    private void trainRobot(@NotNull String robotId) {
        //开始训练
        HttpResponse train = nluApi.train(robotId);
        JSONObject jsonObject = JSONObject.parseObject(train.getBody());
        Assert.assertEquals(jsonObject.get("message"), "ok");
        Object data1 = AssertUtil.getData(train, "$.data");
        String modelIds = AssertUtil.getData(train, "$.data.modelTaskIds.[0]").toString();
        Assert.assertNotNull(data1);

        //开始模型训练并等待进度为100%
        HttpResponse httpResponse6 = trainProgress.executeUntil(nluApi, "100", 3000, robotId, modelIds);
        String progress = AssertUtil.getData(httpResponse6, "$.data.progress").toString();
        System.out.println("progress = " + progress);
        Assert.assertEquals(progress, "100");
    }

    /**
     * 删除机器人
     *
     * @param id
     */
    private void deleteRobot(@NotNull String id) {
        //删除机器人到回收站
        HttpResponse httpResponse2 = robotApi.deleteRobotToCollection(id);
        //System.out.println("httpResponse = " + httpResponse2);
        JSONObject body1 = JSONObject.parseObject(httpResponse2.getBody());
        Assert.assertEquals(body1.get("message"), "请求成功");

        //从回收站删除
        HttpResponse httpResponse3 = robotApi.finalDelete(id);
        System.out.println("httpResponse3 = " + httpResponse3);
        JSONObject body3 = JSONObject.parseObject(httpResponse3.getBody());
        Assert.assertEquals(body3.get("message"), "请求成功");
    }

    /**
     * 仅机器人策略分配
     */
    private void addStrategyOnlyRobot() {
        //1.新建仅人工渠道
        aBasicApi.switchLogin(0);
        String strategyName = "策略" + TimeUtil.getNowTime("yyyyMMddHHMMSS");
        HttpResponse httpResponse = sessionDistributeStrategyApi.addStrategyOnlyRobot(strategyName, this.productID, this.robotId);
        this.strategyID = aSessionDistributeStrategyApi.getStrategyID(strategyName);

        aBasicApi.cLogin(this.chatToken);
        HttpResponse httpResponse1 = chatApi.initSession(this.chatToken);
        AssertUtil.assertBodyEquals(httpResponse1, "$.data.strategy", "only_robot");
    }
}
