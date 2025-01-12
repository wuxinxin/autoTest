package com.example.dztest.dataDriven.data;

import com.alibaba.excel.EasyExcel;
import com.example.dztest.apicase.CaseHandler;
import com.example.dztest.apicase.listeners.CaseFileListener;
import com.example.dztest.apicase.model.ApiCaseModel;
import com.example.dztest.dataDriven.listeners.KmsTestFileListener;
import com.example.dztest.dataDriven.model.KmsModel;
import org.apache.commons.compress.utils.Lists;
import org.testng.ITestContext;
import org.testng.annotations.DataProvider;

import java.lang.reflect.InvocationTargetException;
import java.util.*;

/**
 * @description: 数据驱动
 * @author xinxin.wu
 * @date 2024/01/11
 * @version: 1.0
 */
public class KmsTestDataProvider {

    /**
     * @description: 获取数据驱动数据
     * @param context
     * @return Iterator<Object>
     */
    @DataProvider(name = "kmsData")
    public static Iterator<Object[]> data(ITestContext context) throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {

        //从xml测试套文件获取参数
        String case_file = "cases/call_test_data.xlsx";
//        String sheets = context.getCurrentXmlTest().getParameter("sheets");
        //读取excel用例文件对应sheet页
        KmsTestFileListener kmsTestFileListener = new KmsTestFileListener();
        EasyExcel.read(case_file, KmsModel.class, kmsTestFileListener).sheet().doRead();
        List<KmsModel> kmsModelList = Optional.ofNullable(kmsTestFileListener.getKmsModelList()).orElse(Lists.newArrayList());

        List<Object[]> caseListItr = new ArrayList<>();
        for (Object obj : kmsModelList) {
            caseListItr.add(new Object[]{obj});
        }

        return caseListItr.iterator();
    }

}
