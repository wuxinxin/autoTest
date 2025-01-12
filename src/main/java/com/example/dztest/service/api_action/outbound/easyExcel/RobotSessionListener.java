package com.example.dztest.service.api_action.outbound.easyExcel;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.example.dztest.domain.outbound.MatchData;
import com.example.dztest.domain.outbound.RobotQAData;
import com.example.dztest.domain.outbound.RobotSessionData;
import org.apache.commons.compress.utils.Lists;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class RobotSessionListener extends AnalysisEventListener<RobotSessionData> {
    //创建list集合封装最终的数据
    List<RobotSessionData> robotSessionDataList = new ArrayList<RobotSessionData>();

    private int rowIndex;

    //一行一行去读取excle内容
    @Override
    public void invoke(RobotSessionData robotSessionData, AnalysisContext analysisContext) {
        System.out.println("***"+robotSessionData);
        robotSessionData.setRowIndex(rowIndex);

        if (null != robotSessionData.getQuestion()) {
            String anser = robotSessionData.getExp_answer();
            if (anser != null ) {
                robotSessionData.getExp_answerList().add(robotSessionData.getExp_answer());
            }

            String matchDataSource = robotSessionData.getExp_matchDataSource();
            String matchDataValue = robotSessionData.getExp_matchDataValue();
            if (matchDataSource != null || matchDataValue != null) {
                robotSessionData.getExp_matchDataList().add(new MatchData(matchDataSource, matchDataValue));
            }

            robotSessionDataList.add(robotSessionData);
        } else {
            RobotSessionData robotSD = robotSessionDataList.get(robotSessionDataList.size() - 1);
            String anser = robotSessionData.getExp_answer();
            if (anser != null ) {
                robotSD.getExp_answerList().add(anser);
            }

            String matchDataSource = robotSessionData.getExp_matchDataSource();
            String matchDataValue = robotSessionData.getExp_matchDataValue();
            if (matchDataSource != null || matchDataValue != null) {
                robotSD.getExp_matchDataList().add(new MatchData(matchDataSource, matchDataValue));
            }
        }

        rowIndex++;
    }

    //读取excel表头信息
    @Override
    public void invokeHeadMap(Map<Integer, String> headMap, AnalysisContext context) {
        System.out.println("表头信息："+headMap);
    }

    //读取完成后执行
    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {
    }

    public List<RobotSessionData> getRobotSessionDataList() {
        return robotSessionDataList;
    }

}
