package com.example.dztest.dataDriven.listeners;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.example.dztest.apicase.model.ApiCaseModel;
import com.example.dztest.dataDriven.model.KmsModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @description: 读取excel测试数据文件
 * @author xinxin.wu
 * @date 2024/01/11
 * @version: 1.0
 */
public class KmsTestFileListener extends AnalysisEventListener<KmsModel> {
    /**
     * 创建list集合封装最终的数据
     */
    List<KmsModel> kmsModelList = new ArrayList<KmsModel>();

    /**
     * @description: 一行一行去读取excle内容
     * @param kmsModel
     * @param analysisContext
     * @return void
     */
    @Override
    public void invoke(KmsModel kmsModel, AnalysisContext analysisContext) {
        System.out.println("***" + kmsModel);
        kmsModelList.add(kmsModel);
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

    public List<KmsModel> getKmsModelList() {
        return kmsModelList;
    }

}
