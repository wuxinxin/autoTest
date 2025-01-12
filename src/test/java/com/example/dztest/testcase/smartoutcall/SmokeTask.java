package com.example.dztest.testcase.smartoutcall;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.dztest.common.annotations.MValue;
import com.example.dztest.domain.HttpResponse;
import com.example.dztest.service.BaseOfTestCase;
import com.example.dztest.service.api_action.crm.ASmsSendControllerApi;
import com.example.dztest.service.api_action.outbound.AAudioRecordController;
import com.example.dztest.service.api_action.outbound.AAudioRecordV2ControllerApi;
import com.example.dztest.service.api_action.outbound.AOutboundTaskManagerV2ControllerApi;
import com.example.dztest.service.interfaces.basic.BasicCustomerBlacklistGroupRelationControllerApi;
import com.example.dztest.service.interfaces.basic.MealOrderControllerApi;
import com.example.dztest.service.interfaces.outbound.AudioRecordV2ControllerApi;
import com.example.dztest.service.interfaces.outbound.OutboundNoGroupsControllerApi;
import com.example.dztest.service.interfaces.outbound.OutboundTaskManagerV2ControllerApi;
import com.example.dztest.utils.AssertUtil;
import com.example.dztest.utils.SslHandshakeExc_NsanMatchingIp;
import com.example.dztest.utils.TimeUtil;
import com.example.dztest.utils.audio.VedioUtil;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
@Epic("语音机器人")
@Feature("智能外呼-外呼任务")
public class SmokeTask extends BaseOfTestCase {

    @Autowired
    private MealOrderControllerApi mealOrderControllerApi;

    @Autowired
    private OutboundTaskManagerV2ControllerApi outboundTaskManagerV2ControllerApi;

    @Autowired
    private AOutboundTaskManagerV2ControllerApi aOutboundTaskManagerV2ControllerApi;


    @Autowired
    private OutboundNoGroupsControllerApi outboundNoGroupsControllerApi;

    @Autowired
    private AudioRecordV2ControllerApi audioRecordV2ControllerApi;

    @Autowired
    private AAudioRecordV2ControllerApi aAudioRecordV2ControllerApi;

    @Autowired
    private AAudioRecordController aAudioRecordController;

    @Autowired
    private ASmsSendControllerApi aSmsSendControllerApi;

    @Autowired
    private BasicCustomerBlacklistGroupRelationControllerApi basicCustomerBlacklistGroupRelationControllerApi;

    @MValue("smartoutcall.TaskRobotID")
    private String robotID;

    @MValue("smartoutcall.robotName")
    private String robotName;

    @MValue("smartoutcall.groupId")
    private String groupId;

    @MValue("smartoutcall.phone")
    private String phone;

    @MValue("speech.outboundNumberGroupName")
    private String outboundNoGroupName;

    private String file = "外呼名单上传虚拟1.csv";

    //任务名
    private String taskName;
    //任务类型
    private Integer taskType;
    //任务流程id
    private String dialogTaskId;
    //外呼号码组
    private String outboundNoGroup;
    //AI座席数
    private Integer aiSeatsNum;
    //名单id
    private String fileId;
    //与 名单id 相同值
    private String callListTask;
    //机器人id
//    private String robotId;
    //机器人名字
//    private String robotName;
    //任务id
    private Integer taskId;
    //当天短信数
    private Integer smsNum;
    private String ackFileId;

    /*通过机器人id获得主流程id*/
    private String getDialogTaskIdByRobotId(String robotId, String engineName) {
        HttpResponse httpResponse = outboundTaskManagerV2ControllerApi.getDialogTask();
        JSONObject body = JSONObject.parseObject(httpResponse.getBody());
        JSONArray data = (JSONArray) body.get("data");

        for (int i = 0; i < data.size(); i++) {
            if (robotId.equals(data.getJSONObject(i).get("robotId"))) {
                JSONArray engineInfoList = (JSONArray) data.getJSONObject(i).get("engineInfoList");

                for (int j = 0; j < engineInfoList.size(); j++) {
                    if (engineName.equals(engineInfoList.getJSONObject(j).get("engineName").toString())) {
                        String dialogTaskId = (String) engineInfoList.getJSONObject(j).get("engineId");
                        return dialogTaskId;
                    }
                }
            }
        }
        return null;
    }

    /*获取“模拟网关*对应的groupId*/
    private String getGroupId(String groupName) {
        HttpResponse httpResponse = outboundNoGroupsControllerApi.getGroupList();
        JSONObject body = JSONObject.parseObject(httpResponse.getBody());
        JSONArray data = (JSONArray) body.get("data");

        for (int i = 0; i < data.size(); i++) {
            if (groupName.equals(data.getJSONObject(i).getString("groupName"))) {
                String groupId = data.getJSONObject(i).getString("groupId");
                return groupId;
            }
        }
        return null;
    }

    @BeforeClass
    public void setUp() {
        aBasicApi.login();

        this.taskName = "outCall-" + TimeUtil.getNowTime("yyyyMMdd-HHMMSS");
        //外呼任务为手动外呼任务
        this.taskType = 1;
        this.outboundNoGroup = this.getGroupId(this.outboundNoGroupName);
        this.aiSeatsNum = 1;
        //9 检测 短信发送
        HttpResponse httpResponse = aSmsSendControllerApi.getTodayRecordList();
        JSONArray smsData = (JSONArray) AssertUtil.getData(httpResponse, "$.data");
        this.smsNum = smsData.size();

        HttpResponse httpResponse1 = basicCustomerBlacklistGroupRelationControllerApi.getBlackGroupMember(this.groupId, this.phone);
        JSONArray jsonArray = (JSONArray) AssertUtil.getData(httpResponse1, "$.data.list");
        if (0 != jsonArray.size()) {
            String id = jsonArray.getJSONObject(0).getString("id");
            basicCustomerBlacklistGroupRelationControllerApi.delBlackGroupMember(id);
        }

        //建立远程连接
//        remoteSipp.connection();
//
//        remoteSipp.runOutboundSipp();
    }

    @Test
    @Story("手动外呼任务")
    public void smartOutCall() {
        //机器人使用：自动化task机器人，流程使用：1主流程
        this.dialogTaskId = this.getDialogTaskIdByRobotId(robotID, "1主流程");

        //1.创建手动外呼任务
        List<String> words = new ArrayList<>();
        words.add(robotID);
        words.add(this.dialogTaskId);

        HttpResponse httpResponse1 = outboundTaskManagerV2ControllerApi.createCallTask(words, this.taskName, this.taskType,
                this.dialogTaskId, this.outboundNoGroup, this.aiSeatsNum, "", robotID);

        AssertUtil.assertBodyEquals(httpResponse1, "$.code", "10000");
        AssertUtil.assertBodyEquals(httpResponse1, "$.data.taskName", this.taskName);
        AssertUtil.assertBodyEquals(httpResponse1, "$.data.outboundNoGroup", this.outboundNoGroup);
        AssertUtil.assertBodyEquals(httpResponse1, "$.data.robotId", robotID);
        this.taskId = (Integer) AssertUtil.getData(httpResponse1, "$.data.id");

        //2.上传名单
        HttpResponse httpResponse = aOutboundTaskManagerV2ControllerApi.importList(this.robotID, this.file);
        AssertUtil.assertBodyEquals(httpResponse, "$.code", "10000");
        this.ackFileId = String.valueOf(AssertUtil.getData(httpResponse, "$.data.ackFileId"));

        //确认上传
        HttpResponse httpResponse12 = outboundTaskManagerV2ControllerApi.importListAndAck(this.taskId, this.ackFileId);
        AssertUtil.assertBodyEquals(httpResponse12, "$.code", "10000");

        //3.1.查看任务监控-任务状态,状态是"未开始"; taskStatus 1,未开始  2，进行中  3，已完成
        HttpResponse httpResponse2 = outboundTaskManagerV2ControllerApi.getCallTaskInfo(this.taskId);
        AssertUtil.assertBodyEquals(httpResponse2, "$.data.taskStatus", 1);

        //3.2.查看进行中列表，通话状态都是"未开始"；callStatus 1，未开始  2，等待接听 3，通话中 7，已接通
        TimeUtil.sleep(2);
        HttpResponse httpResponse3 = audioRecordV2ControllerApi.getRunningTaskCustomerList(this.taskId);
        AssertUtil.assertBodyEquals(httpResponse3, "$.code", "10000");
        JSONArray taskCustomerList = (JSONArray) AssertUtil.getData(httpResponse3, "$.data.list");
        for (int i = 0; i < taskCustomerList.size(); i++) {
            Assert.assertEquals(taskCustomerList.getJSONObject(i).get("callStatus"), 1);
        }

        //呼入前，取资源包中，呼入机器人分钟数、线路呼入剩余分钟数
        logger.info("呼入前，获取当前资源包余额");
        HttpResponse httpResponse14 = mealOrderControllerApi.getResourceTotal();
        JSONObject body_new1 = JSONObject.parseObject(httpResponse14.getBody());
        JSONObject data_new1 = (JSONObject) body_new1.get("data");
        JSONArray resourceN = (JSONArray) data_new1.get("resource");
        JSONObject resource1 = resourceN.getJSONObject(1);
        JSONObject resource3 = resourceN.getJSONObject(3);
        Integer LineInboundMinutes = Integer.valueOf(resource1.get("resource").toString());
        Integer RobotInboundMinutes = Integer.valueOf(resource3.get("resource").toString());

        //4.启动外呼任务
        HttpResponse httpResponse4 = outboundTaskManagerV2ControllerApi.startCallTask(this.taskId);
        AssertUtil.assertBodyEquals(httpResponse4, "$.data", true);

        //5.1.查看任务详情，任务状态为"进行中"
        HttpResponse httpResponse5 = outboundTaskManagerV2ControllerApi.getCallTaskInfo(this.taskId);
        AssertUtil.assertBodyEquals(httpResponse5, "$.data.taskStatus", 2);

        boolean b = this.checkUtil(audioRecordV2ControllerApi, true, 1000, this.taskId);
        Assert.assertTrue(b);

        //6.电话接通，与机器人交互(sipp脚本完成)
        TimeUtil.sleep(120);
        //7.任务完成
        //7.1 检测 任务状态为”3 已完成“
        HttpResponse httpResponse7 = outboundTaskManagerV2ControllerApi.getCallTaskInfo(this.taskId);
        AssertUtil.assertBodyEquals(httpResponse7, "$.data.taskStatus", 3);
        //7.2 检测 任务-已完成列表下:通话状态全为“7=已接通”,对话标签为“已预警”，信息采集 变量key为"是否遇到诈骗" value为”不清楚“
        HttpResponse httpResponse8 = outboundTaskManagerV2ControllerApi.getRecordListPage(this.taskId);
        JSONArray jsonArray = (JSONArray) AssertUtil.getData(httpResponse8, "$.data.list");

        //校验对话标签
        String aitagname = (String) AssertUtil.getData(httpResponse8, "$.data.list[0].aitagname");
        Assert.assertEquals(aitagname, "已预警");

        //校验信息采集是否成功
        for (int i = 0; i < jsonArray.size(); i++) {
            JSONObject extendInfo = jsonArray.getJSONObject(i).getJSONObject("extendInfo");
            Assert.assertEquals(extendInfo.get("是否遇到诈骗"), "不清楚");
        }
        Integer recordid0 = (Integer) AssertUtil.getData(httpResponse8, "$.data.list[0].id");

        //7.3 检测 已完成列表下，通话详情中聊天记录数据
        String sessionid0 = (String) AssertUtil.getData(httpResponse8, "$.data.list[0].sessionId");
        Assert.assertTrue(aAudioRecordV2ControllerApi.checkData(sessionid0, "robot_session.xlsx"));

        //8.1 检测 智能语音-通话记录-外呼记录 列表
        HttpResponse httpResponse9 = aAudioRecordController.getTodayRecordList();
        AssertUtil.assertBodyEquals(httpResponse9, "$.data.list[0].id", recordid0);
        AssertUtil.assertBodyEquals(httpResponse9, "$.data.list[0].taskname", this.taskName);
        //8.2 检测 外呼拨打详情信息接口
        HttpResponse httpResponse10 = audioRecordV2ControllerApi.getRecordCallDetail(recordid0);
        AssertUtil.assertBodyEquals(httpResponse10, "$.data.sessionId", sessionid0);
        //呼入后，对比资源包中，呼入机器人分钟数、线路呼入剩余分钟数
        HttpResponse httpResponse15 = mealOrderControllerApi.getResourceTotal();
        AssertUtil.assertBodyEquals(httpResponse15, "$.data.resource[1].resource", LineInboundMinutes-1);
        AssertUtil.assertBodyEquals(httpResponse15, "$.data.resource[3].resource", RobotInboundMinutes-1);
        //8.3 检测 录音
        HttpResponse httpResponse11 = audioRecordV2ControllerApi.getAudioUrlAndContents(sessionid0);
        String url = (String) AssertUtil.getData(httpResponse11, "$.data.audioDTO.url");
        //录音检测 下载录音
        try {
            String filePath = SslHandshakeExc_NsanMatchingIp.download(url);
            //录音检测 比对录音时长
            long time = VedioUtil.getDuration(filePath);
            logger.info("time : {}", time);
        } catch (Exception e) {
            logger.info("下载录音异常信息: " + e);
            logger.error("下载文件或获取录音时长失败");
        }

    }

    @Test
    @Story("对话短信")
    public void sms() {
        //检测 短信发送
        HttpResponse httpResponse = aSmsSendControllerApi.getTodayRecordList();
        JSONArray smsData = (JSONArray) AssertUtil.getData(httpResponse, "$.data");
        //短信数量+3
        Assert.assertEquals(smsData.size(), this.smsNum + 1);
        //校验机器人名称
        AssertUtil.assertBodyEquals(httpResponse, "$.data[0].userName", "机器人-" + robotName);
        //校验短信模板名称
        AssertUtil.assertBodyEquals(httpResponse, "$.data[0].templateName", "短信测试");
    }

    @Test
    @Story("黑名单规则")
    public void black() {
        HttpResponse httpResponse = basicCustomerBlacklistGroupRelationControllerApi.addBlackGroupMember(this.groupId,"呼出测试11",this.phone);
        AssertUtil.assertBodyEquals(httpResponse,"$.code","10000");
        HttpResponse httpResponse1 = basicCustomerBlacklistGroupRelationControllerApi.getBlackGroupMember(this.groupId, this.phone);
        AssertUtil.assertBodyEquals(httpResponse1, "$.data.list[0].phone", this.phone);
    }

    private boolean checkUtil(AudioRecordV2ControllerApi func, Object expect, Integer delay, Integer taskId) {
        boolean isCalled = false;

        long startTime = System.currentTimeMillis();
        while (true) {
            //5.2.查看进行中列表，其中有通话状态为"等待接听或通话中"
            HttpResponse httpResponse6 = func.getRunningTaskCustomerList(this.taskId);
            AssertUtil.assertBodyEquals(httpResponse6, "$.code", "10000");
            JSONArray taskCustomerList2 = (JSONArray) AssertUtil.getData(httpResponse6, "$.data.list");

            String callStatus = taskCustomerList2.getJSONObject(0).get("callStatus").toString();

            try {
                Thread.sleep(delay);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            long endTime = System.currentTimeMillis();
            long takenTime = endTime - startTime;

            System.out.println("当前进行中任务的callStatus为: " + callStatus + " => (2=等待接听，3=通话中)" +
                    "，当前检测耗时: " + takenTime / 1000 + "秒");

            if ("2".equals(callStatus) || "3".equals(callStatus)) {
                System.out.println("满足条件，当前进行中任务的callStatus为: " + callStatus + " => (2=等待接听，3=通话中)" +
                        "，当前检测耗时: " + takenTime / 1000 + "秒，开始与机器人对话120s");
                isCalled = true;
                break;
            }

            if (takenTime >= 60000) {
                //超过60s还没检测到对应通话中|等待接听的状态认为失败
                System.out.println("检测超时，当前进行中任务的callStatus为: " + callStatus + " => (2=等待接听，3=通话中)" +
                        "，当前检测耗时: " + takenTime / 1000 + "秒");
                isCalled = false;
                break;
            }
        }
        return isCalled;
    }

}
