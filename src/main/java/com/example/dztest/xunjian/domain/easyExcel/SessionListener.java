package com.example.dztest.xunjian.domain.easyExcel;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.example.dztest.xunjian.domain.SessionData;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author xinxin.wu
 * @description: 读取期望会话信息--监听器
 * @date 2023/08/10
 * @version: 1.0
 */
public class SessionListener extends AnalysisEventListener<SessionData> {
    /**
     * 创建list集合封装最终的数据
     */
    List<SessionData> sessionDataList = new ArrayList<>();

    /**
     * 一行一行去读取excle内容
     */
    @Override
    public void invoke(SessionData sessionData, AnalysisContext analysisContext) {
        System.out.println("***"+sessionData);
        sessionDataList.add(sessionData);
    }

    /**
     * 读取excel表头信息
     */
    @Override
    public void invokeHeadMap(Map<Integer, String> headMap, AnalysisContext context) {
        System.out.println("表头信息："+headMap);
    }

    /**
     * 读取完成后执行
     */
    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {
    }


    public List<SessionData> getSessionDataList() {
        return sessionDataList;
    }
}
