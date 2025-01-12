package com.example.dztest.service.api_action.outbound.easyExcel;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.example.dztest.domain.outbound.RobotQAData;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class RobotQAListener extends AnalysisEventListener<RobotQAData> {
    //创建list集合封装最终的数据
    List<RobotQAData> robotQADataList = new ArrayList<RobotQAData>();

    private int rowIndex;

    //一行一行去读取excle内容
    @Override
    public void invoke(RobotQAData robotQAData, AnalysisContext analysisContext) {
        System.out.println("***"+robotQAData);
        robotQAData.setRowIndex(rowIndex);
        robotQADataList.add(robotQAData);
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


    public List<RobotQAData> getRobotQADataList() {
        return robotQADataList;
    }
}
