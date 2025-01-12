package com.example.dztest.xunjian.domain;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import java.util.ArrayList;
import java.util.List;

/**
 * @author xinxin.wu
 * @description: 输出会话信息实体
 * @date 2023/08/11
 * @version: 1.0
 */
@Data
public class SessionOutData {
    @ExcelIgnore
    protected final Logger logger = LoggerFactory.getLogger(this.getClass());

    @ExcelProperty(value = "模块", index = 0)
    private String module;

    @ExcelProperty(value = "任务名", index = 1)
    private String taskName;

    @ExcelProperty(value = "机器人", index = 2)
    private String robotName;

    @ExcelProperty(value = "通话状态", index = 3)
    private String callStatusDesc;

    @ExcelProperty(value = "会话", index = 4)
    private String sessionId;

    @ExcelProperty(value = "电话", index = 5)
    private String phone;

    @ExcelProperty(value = "会话时长-实际", index = 6)
    private Integer actTime;

    @ExcelProperty(value = "AI分类-实际", index = 7)
    private String actAiTag;

    @ExcelProperty(value = "会话时长-期望", index = 8)
    private Integer expTime;

    @ExcelProperty(value = "AI分类-期望", index = 9)
    private String expAiTag;

    @ExcelProperty(value = "对话检测", index = 10)
    private Boolean sessionCheck;

    @ExcelIgnore
    private Integer status;

    /**
     * 机器人对话列表,实际
     */
    @ExcelIgnore
    private List<RobotQaData> actRobotQaDataList = new ArrayList<>();

    /**
     * 机器人对话列表，期望
     */
    @ExcelIgnore
    private List<RobotQaData> expRobotQaDataList = new ArrayList<>();

    /**
     * @description: 设置期望的会话时长和ai标签
     * @param sessionDataList 期望的会话信息实体列表
     * @return void
     */
    public void setExp(List<SessionData> sessionDataList) {
        for (SessionData sessionData : sessionDataList) {
            String ePhone = sessionData.getPhone();
            if (ePhone.equals(this.phone)) {
                this.expAiTag = sessionData.getAitagname();
                this.expTime = sessionData.getTime();
                break;
            }
        }
    }

    /**
     * @description: 比较期望和实际会话信息；比较会话中期望和实际对话
     * @param
     * @return Boolean
     */
    public Boolean checkData() {
        Integer successStatus = 7;
        Integer range = 3;

        //未接通，直接返回false
        if (!this.status.equals(successStatus)) {
            logger.error("actStatus: {}; expStatus: {}", this.status, successStatus);
            return false;
        }

        if (!this.expAiTag.equals(this.actAiTag)) {
            logger.error("actAiTag: {}; expAITag: {}", this.actAiTag, this.expAiTag);
            return false;
        }
        if ((this.actTime < (this.expTime - range)) || (this.actTime > (this.expTime + range))) {
            logger.error("actTime: {}; expTime: {}", this.actTime, this.expTime);
            return false;
        }

        //比较apiRobotQADataList和robotQADataList数据是否相同
        for (int i = 0; i < this.actRobotQaDataList.size(); i++) {
            logger.info("sessionId: {}", this.sessionId);
            if (!this.expRobotQaDataList.get(i).equals(this.actRobotQaDataList.get(i))) {
                logger.error("excelMatchInfo: {}; apiMatchInfo: {}", this.expRobotQaDataList.get(i).getMatchInfo(), this.actRobotQaDataList.get(i).getMatchInfo());
                logger.error("excelIntention: {}; apiIntention: {}", this.expRobotQaDataList.get(i).getIntention(), this.actRobotQaDataList.get(i).getIntention());
                logger.error("excelType0content: {}; apiType0content: {}", this.expRobotQaDataList.get(i).getType0content(), this.actRobotQaDataList.get(i).getType0content());
                logger.error("excelType1content: {}; apiType1content: {}", this.expRobotQaDataList.get(i).getType1content(), this.actRobotQaDataList.get(i).getType1content());
                return false;
            }
        }

        return true;
    }


}
