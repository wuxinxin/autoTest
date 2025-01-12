package com.example.dztest.testcase.dataDriven;

import com.alibaba.excel.EasyExcel;
import com.example.dztest.dataDriven.data.KmsTestDataProvider;
import com.example.dztest.dataDriven.model.KmsModel;
import com.example.dztest.domain.GlobalVar;
import com.example.dztest.domain.HttpResponse;
import com.example.dztest.service.api_action.ABasicApi;
import com.example.dztest.service.interfaces.basic.BasicAuthControllerApi;
import com.example.dztest.service.interfaces.chat.ChatApi;
import com.example.dztest.utils.AssertUtil;
import io.qameta.allure.Story;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * @description: TODO
 * @author xinxin.wu
 * @date 2024/01/11
 * @version: 1.0
 */
@SpringBootTest
public class kmsDataDriven extends AbstractTestNGSpringContextTests {

    @Autowired
    private BasicAuthControllerApi basicAuthControllerApi;

    @Autowired
    ChatApi chatApi;

    @Autowired
    ABasicApi aBasicApi;

    private String chatToken;

    private String origin_auth;

    private String sessionId;

    private String productID = "94afe34b5f734b10a5357391395da356";

    private List<KmsModel> kmsModelList = new ArrayList<KmsModel>();

    @BeforeClass
    public void beforeClass() {
        aBasicApi.login();

        //获取c端chat token
        HttpResponse httpResponse6 = basicAuthControllerApi.getChatToken(this.productID);
        this.chatToken = (String) AssertUtil.getData(httpResponse6, "$.data");

        //临时变更aut
        this.origin_auth = GlobalVar.GLOBAL_DATA_MAP.get("Authorization").toString();
        GlobalVar.GLOBAL_DATA_MAP.put("Authorization", this.chatToken);

        HttpResponse httpResponse1 = chatApi.initSession(this.chatToken);
        AssertUtil.assertBodyEquals(httpResponse1, "$.message", "success");
        this.sessionId = AssertUtil.getData(httpResponse1, "$.data.sessionId").toString();
    }

    @AfterClass
    public void afterClass() {
        //恢复auth
        GlobalVar.GLOBAL_DATA_MAP.put("Authorization", this.origin_auth);

        //输出excel
        String excelSession = "cases/out_call_test_data.xlsx";
        // 这里 需要指定写用哪个class去写，然后写到第一个sheet，名字为模板 然后文件流会自动关闭
        EasyExcel.write(excelSession, KmsModel.class).sheet().doWrite(this.kmsModelList);
    }

    @Test(dataProvider = "kmsData", dataProviderClass = KmsTestDataProvider.class, threadPoolSize = 1)
    @Story("单个接口执行b")
    public void autoTest(KmsModel kmsModel) {
        //开始验证问答知识
        HttpResponse httpResponse = chatApi.answer(kmsModel.getText(), this.chatToken, this.chatToken, this.sessionId, this.chatToken);


        String classifyName = null;
        String answerSource = null;
        String knowledgeSource = null;
        try {
            classifyName = (String) AssertUtil.getData(httpResponse, "$.data.classifyName");
            answerSource = (String) AssertUtil.getData(httpResponse, "$.data.mockAnswer.answerSource");
            knowledgeSource = (String) AssertUtil.getData(httpResponse, "$.data.mockAnswer.knowledgeSource");
        } catch (Exception e) {
            System.out.println(e.toString());
        }

        kmsModel.setActLabel(classifyName);
        kmsModel.setAnswerSource(answerSource);
        kmsModel.setKnowledgeSource(knowledgeSource);

        kmsModelList.add(kmsModel);
        kmsModel.assertion();
    }
}
