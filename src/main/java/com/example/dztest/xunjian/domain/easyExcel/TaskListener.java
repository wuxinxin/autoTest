package com.example.dztest.xunjian.domain.easyExcel;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.example.dztest.xunjian.domain.TaskData;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author xinxin.wu
 * @description: 读取需要新建的外呼任务--监听器
 * @date 2023/08/10
 * @version: 1.0
 */
public class TaskListener extends AnalysisEventListener<TaskData> {
    /**
     * 创建list集合封装最终的数据
     */
    List<TaskData> taskDataList = new ArrayList<>();

    /**
     * 一行一行去读取excel内容
     */
    @Override
    public void invoke(TaskData taskData, AnalysisContext analysisContext) {
        System.out.println("***"+taskData);

        if (taskData.getNum() == 0 ) {
            return;
        }

        taskDataList.add(taskData);
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

    public List<TaskData> getTaskDataList() {
        return taskDataList;
    }
}
