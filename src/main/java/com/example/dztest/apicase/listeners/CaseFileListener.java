package com.example.dztest.apicase.listeners;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.example.dztest.apicase.model.ApiCaseModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author xinxin.wu
 * @description: 读取excel用例文件
 * @date 2023/10/18
 * @version: 1.0
 */
public class CaseFileListener extends AnalysisEventListener<ApiCaseModel> {
    /**
     * 创建list集合封装最终的数据
     */
    List<ApiCaseModel> apiCaseModelList = new ArrayList<ApiCaseModel>();

    private int rowIndex = 2;

    /**
     * @description: 一行一行去读取excle内容
     * @param apiCaseModel
     * @param analysisContext
     * @return void
     */
    @Override
    public void invoke(ApiCaseModel apiCaseModel, AnalysisContext analysisContext) {
        System.out.println("***" + apiCaseModel);
        apiCaseModel.setRowIndex(rowIndex);

        apiCaseModelList.add(apiCaseModel);
        rowIndex++;
    }

    /**
     * @description: 读取excel表头信息
     * @param headMap
     * @param context
     * @return void
     */
    @Override
    public void invokeHeadMap(Map<Integer, String> headMap, AnalysisContext context) {
        System.out.println("表头信息：" + headMap);
    }

    /**
     * @description: 读取完成后执行
     * @param analysisContext
     * @return void
     */
    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {
    }

    public List<ApiCaseModel> getApiCaseModelList() {
        return apiCaseModelList;
    }


}
