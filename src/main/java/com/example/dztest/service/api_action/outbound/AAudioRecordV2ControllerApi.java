package com.example.dztest.service.api_action.outbound;

import com.alibaba.excel.EasyExcel;
import com.alibaba.fastjson.JSONArray;
import com.example.dztest.domain.HttpResponse;
import com.example.dztest.domain.outbound.RobotQAData;
import com.example.dztest.service.api_action.outbound.easyExcel.RobotQAListener;
import com.example.dztest.service.interfaces.outbound.AudioRecordV2ControllerApi;
import com.example.dztest.utils.AssertUtil;
import com.example.dztest.utils.TimeUtil;
import org.apache.commons.compress.utils.Lists;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.AbstractList;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class AAudioRecordV2ControllerApi {
    protected final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private AudioRecordV2ControllerApi audioRecordV2ControllerApi;

    /*检验通话记录数据
    * @param session  通话记录sessionid
    * @param excelName  通话记录预期数据保存的excel文件名
    * */
    public Boolean checkData(String session, String excelName){
        String base_path = getClass().getResource("/").getPath();

        String filePath = base_path.replaceFirst("test-classes", "classes") + "static/outbound/" + excelName;
        RobotQAListener robotQAListener = new RobotQAListener();
        EasyExcel.read(filePath, RobotQAData.class, robotQAListener).sheet().doRead();
        List<RobotQAData> robotQADataList = Optional.ofNullable(robotQAListener.getRobotQADataList()).orElse(Lists.newArrayList());

        List<RobotQAData> apiRobotQADataList = new ArrayList<RobotQAData>();
        int rowIndex = 0;
        String type0content = null;
        String type1content = null;
        String intention = null;
        String matchInfo = null;
        HttpResponse httpResponse = audioRecordV2ControllerApi.getAudioUrlAndContents(session);
        JSONArray jsonArray = (JSONArray) AssertUtil.getData(httpResponse, "$.data.audioRecordContentDTOS");
        for (int i = 0; i < jsonArray.size(); i++) {
            int type = (int)jsonArray.getJSONObject(i).get("type");
            if (0 == type && i +1 != jsonArray.size() ) {
                type0content = (String) jsonArray.getJSONObject(i).getString("content").trim();
            } else if (0 == type && i +1 == jsonArray.size()) {
                type0content = (String) jsonArray.getJSONObject(i).getString("content").trim();
                RobotQAData robotQAData = new RobotQAData();
                robotQAData.setRowIndex(rowIndex);
                rowIndex++;
                robotQAData.setType0content(type0content);

                apiRobotQADataList.add(robotQAData);
            } else if (1 == type) {
                type1content = (String) jsonArray.getJSONObject(i).getString("content").trim();
                intention = (String) jsonArray.getJSONObject(i).getString("intention").trim();
                matchInfo = (String) jsonArray.getJSONObject(i).getString("matchInfo").trim();

                RobotQAData robotQAData = new RobotQAData();
                robotQAData.setRowIndex(rowIndex);
                rowIndex++;
                robotQAData.setType0content(type0content);
                robotQAData.setType1content(type1content);
                robotQAData.setMatchInfo(matchInfo);
                robotQAData.setIntention(intention);

                apiRobotQADataList.add(robotQAData);
            } else {
                logger.warn("type不匹配");
            }
        }

        //比较apiRobotQADataList和robotQADataList数据是否相同
        for (int i = 0; i < robotQADataList.size(); i++) {
            if ( !robotQADataList.get(i).equals(apiRobotQADataList.get(i)) ) {
                logger.error("excelMatchInfo: {}; apiMatchInfo: {}" , robotQADataList.get(i).getMatchInfo(), apiRobotQADataList.get(i).getMatchInfo());
                logger.error("excelIntention: {}; apiIntention: {}" , robotQADataList.get(i).getIntention(), apiRobotQADataList.get(i).getIntention());
                logger.error("excelType0content: {}; apiType0content: {}" , robotQADataList.get(i).getType0content(), apiRobotQADataList.get(i).getType0content());
                logger.error("excelType1content: {}; apiType1content: {}" , robotQADataList.get(i).getType1content(), apiRobotQADataList.get(i).getType1content());
                return false;
            }
        }

        return true;
    }

}
