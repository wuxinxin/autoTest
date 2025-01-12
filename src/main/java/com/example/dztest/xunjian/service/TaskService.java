package com.example.dztest.xunjian.service;

import com.alibaba.excel.EasyExcel;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.dztest.domain.HttpResponse;
import com.example.dztest.service.interfaces.outbound.AudioRecordV2ControllerApi;
import com.example.dztest.service.interfaces.outbound.OutboundTaskManagerV2ControllerApi;
import com.example.dztest.utils.AssertUtil;
import com.example.dztest.utils.SpringContextUtil;
import com.example.dztest.utils.TimeUtil;
import com.example.dztest.xunjian.domain.RobotQaData;
import com.example.dztest.xunjian.domain.SessionData;
import com.example.dztest.xunjian.domain.easyExcel.RobotQaListener;
import com.example.dztest.xunjian.domain.easyExcel.SessionListener;
import org.apache.commons.compress.utils.Lists;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * @author xinxin.wu
 * @description: 外呼任务-数据处理
 * @date 2023/08/11
 * @version: 1.0
 */
@Service
public class TaskService {
    protected final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private OutboundTaskManagerV2ControllerApi outboundTaskManagerV2ControllerApi;

    @Autowired
    private AudioRecordV2ControllerApi audioRecordV2ControllerApi;

    /**
     * @description: 得到上传名单文件地址
     * @param module 模块名称
     * @return String
     */
    public String getPhoneFile(String module) {
        //当前环境
        String env = SpringContextUtil.getActiveProfile();

        String basePath = Objects.requireNonNull(getClass().getResource("/")).getPath();
        String filePath = basePath.replaceFirst("test-classes", "classes") + "static/xunjian/巡检号码/巡检号码-" + module + ".csv";
        File file = new File(filePath);
        if (file.exists()) {
            return filePath;
        }

        String filePath1 = basePath.replaceFirst("test-classes", "classes") + "static/xunjian/巡检号码-" + env + "/巡检号码-" + module + ".csv";
        File file1 = new File(filePath);
        if (file1.exists()) {
            return filePath1;
        }

        return null;
    }

    /**
     * @description: 得到任务数据文件地址
     * @param
     * @return String
     */
    public String getTaskDataFile() {
        //当前环境
        String env = SpringContextUtil.getActiveProfile();

        String basePath = Objects.requireNonNull(getClass().getResource("/")).getPath();

        String filePath1 = basePath.replaceFirst("test-classes", "classes") + "static/xunjian/outbound-" + env + ".xlsx";
        File file1 = new File(filePath1);
        if (file1.exists()) {
            return filePath1;
        }

        String filePath = basePath.replaceFirst("test-classes", "classes") + "static/xunjian/outbound.xlsx";
        File file = new File(filePath);
        if (file.exists()) {
            return filePath;
        }

        return null;
    }

    /**
     * @description: 得到对话期望数据文件地址
     * @param module 模块名称
     * @param phone 手机号码
     * @return String
     */
    public String getQaDataFile(String module, String phone) {
        //当前环境
        String env = SpringContextUtil.getActiveProfile();

        String basePath = Objects.requireNonNull(getClass().getResource("/")).getPath();

        String filePath1 = basePath.replaceFirst("test-classes", "classes") + "static/xunjian/对话数据-" + env + "\\" + module + "/" + phone + ".xlsx";
        File file1 = new File(filePath1);
        if (file1.exists()) {
            return filePath1;
        }

        String filePath = basePath.replaceFirst("test-classes", "classes") + "static/xunjian/对话数据/" + module + "/" + phone + ".xlsx";
        File file = new File(filePath);
        if (file.exists()) {
            return filePath;
        }

        return null;
    }

    /**
     * @description: 获得输出excel文件地址
     * @param
     * @return String
     */
    public String getOutFile() {
        //当前环境
        String env = SpringContextUtil.getActiveProfile();

        String basePath = Objects.requireNonNull(getClass().getResource("/")).getPath();

        return basePath.replaceFirst("test-classes", "classes") + "static/xunjian/out/outbound-" + env + "-" + TimeUtil.getNowTime("yyyyMMddHHMMSS") + ".xlsx";
    }

    /**
     * @description: 等待任务结束
     * @param num 循环次数
     * @param taskId 任务id
     * @return Boolean
     */
    public Boolean waitTaskEnd(int num, Integer taskId) {
        int n = 3;
        for (int i = 0; i < num * n; i++) {
            logger.info("当前检测耗时: {}", (i + 1) * 60);
            TimeUtil.sleep(60);

            HttpResponse httpResponse7 = outboundTaskManagerV2ControllerApi.getCallTaskInfo(taskId);
            Integer status = (Integer) AssertUtil.getData(httpResponse7, "$.data.taskStatus");
            //检测 任务状态为”3 已完成“
            if (status == 3) {
                return true;
            }
        }
        return false;
    }

    /**
     * @description: 通过sessionId获取对话信息
     * @param sessionId 会话id
     * @return List<RobotQaData> 会话中对话实体列表
     */
    public List<RobotQaData> getActQaList(String sessionId) {
        List<RobotQaData> apiRobotQaDataList = new ArrayList<>();

        int rowIndex = 0;
        String type0content = null;
        String type1content;
        String intention = null;
        String matchInfo;
        HttpResponse httpResponse = audioRecordV2ControllerApi.getAudioUrlAndContents(sessionId);
        JSONArray jsonArray = (JSONArray) AssertUtil.getData(httpResponse, "$.data.audioRecordContentDTOS");
        for (int i = 0; i < jsonArray.size(); i++) {

            JSONObject jsonObject = jsonArray.getJSONObject(i);

            int type = (int) jsonArray.getJSONObject(i).get("type");
            if (0 == type && i + 1 != jsonArray.size()) {
                type0content = jsonArray.getJSONObject(i).getString("content").trim();
            } else if (0 == type && i + 1 == jsonArray.size()) {
                type0content = jsonObject.getString("content").trim();
                RobotQaData robotQaData = new RobotQaData();
                robotQaData.setRowIndex(rowIndex);
                rowIndex++;
                robotQaData.setType0content(type0content);

                apiRobotQaDataList.add(robotQaData);
            } else if (1 == type) {
                type1content = jsonObject.getString("content").trim();
                if (jsonObject.containsKey("intention")) {
                    intention = jsonObject.getString("intention").trim();
                }
                matchInfo = jsonObject.getString("matchInfo").trim();

                RobotQaData robotQaData = new RobotQaData();
                robotQaData.setRowIndex(rowIndex);
                rowIndex++;
                robotQaData.setType0content(type0content);
                robotQaData.setType1content(type1content);
                robotQaData.setMatchInfo(matchInfo);
                robotQaData.setIntention(intention);

                apiRobotQaDataList.add(robotQaData);
            } else {
                logger.warn("type不匹配");
            }
        }

        return apiRobotQaDataList;
    }

    /**
     * @description: 读取excel，获取会话期望值列表
     * @param module 模块名称
     * @return List<SessionData> 期望会话信息列表
     */
    public List<SessionData> getExpSessionInfo(String module) {
        String outboundFile = this.getTaskDataFile();

        SessionListener sessionListener = new SessionListener();
        EasyExcel.read(outboundFile, SessionData.class, sessionListener).sheet(module).doRead();

        return Optional.ofNullable(sessionListener.getSessionDataList()).orElse(Lists.newArrayList());
    }

    /**
     * @description: 读取excel，获取会话中对话期望值列表
     * @param module 模块名称
     * @param phone 电话号码
     * @return List<RobotQaData> 期望对话实体列表
     */
    public List<RobotQaData> getExpQaList(String module, String phone) {
        String outboundFile = this.getQaDataFile(module, phone);
        RobotQaListener robotQaListener = new RobotQaListener();
        EasyExcel.read(outboundFile, RobotQaData.class, robotQaListener).sheet().doRead();

        return Optional.ofNullable(robotQaListener.getRobotQaDataList()).orElse(Lists.newArrayList());
    }

}
