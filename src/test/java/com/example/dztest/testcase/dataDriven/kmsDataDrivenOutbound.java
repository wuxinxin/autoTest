package com.example.dztest.testcase.dataDriven;

import com.alibaba.excel.EasyExcel;
import com.example.dztest.dataDriven.data.KmsTestDataProvider;
import com.example.dztest.dataDriven.model.KmsModel;
import com.example.dztest.domain.HttpResponse;
import com.example.dztest.service.api_action.ABasicApi;
import com.example.dztest.service.interfaces.outbound.SpeakFlowV2ControllerApi;
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
 * @date 2024/01/16
 * @version: 1.0
 */
@SpringBootTest
public class kmsDataDrivenOutbound extends AbstractTestNGSpringContextTests {

    @Autowired
    ABasicApi aBasicApi;

    @Autowired
    private SpeakFlowV2ControllerApi speakFlowV2ControllerApi;

    private String callRecordId;

    private String sessionId;

    private String robotID;

    private String tenantId;

    private List<KmsModel> kmsModelList = new ArrayList<KmsModel>();

    @BeforeClass
    public void beforeClass() {
        aBasicApi.login();

        this.robotID = "NEWfd1106eef42d589580d4736e5db7e";
        this.tenantId = "zkj16970926742084e2d79d1";
        Integer chatLanguageId = 978190;
        Boolean autoTranslate = false;

        //初始化会话
        HttpResponse httpResponse1 = speakFlowV2ControllerApi.initSessionTest2(this.robotID, this.tenantId, chatLanguageId, autoTranslate);
//        HttpResponse httpResponse1 = speakFlowV2ControllerApi.initSessionTest3(this.robotID, this.tenantId, autoTranslate);

        this.callRecordId = (String) AssertUtil.getData(httpResponse1, "$.data.callRecordId");
        this.sessionId = (String) AssertUtil.getData(httpResponse1, "$.data.sessionId");
    }

    @AfterClass
    public void afterClass() {
        //输出excel
        String excelSession = "cases/out_call_test_data.xlsx";
        // 这里 需要指定写用哪个class去写，然后写到第一个sheet，名字为模板 然后文件流会自动关闭
        EasyExcel.write(excelSession, KmsModel.class).sheet().doWrite(this.kmsModelList);
    }

    @Test(dataProvider = "kmsData", dataProviderClass = KmsTestDataProvider.class, threadPoolSize = 1)
    @Story("单个接口执行b")
    public void autoTest(KmsModel kmsModel) {
        //开始验证问答知识
        HttpResponse httpResponse = speakFlowV2ControllerApi.answerTest(this.callRecordId, this.sessionId, kmsModel.getText());

        String classifyName = null;
        String answerSource = null;
        String knowledgeSource = null;
        try {
            classifyName = (String) AssertUtil.getData(httpResponse, "$.data.classifyName");
        } catch (Exception e) {
            System.out.println(e.toString());
        }

        try {
            answerSource = (String) AssertUtil.getData(httpResponse, "$.data.mockAnswer.answerSource");
        } catch (Exception e) {
            System.out.println(e.toString());
        }

        try {
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
