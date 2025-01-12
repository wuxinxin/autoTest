package com.example.dztest.apicase.data;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.read.metadata.ReadSheet;
import com.example.dztest.apicase.CaseHandler;
import com.example.dztest.apicase.listeners.CaseFileListener;
import com.example.dztest.apicase.model.ApiCaseModel;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.compress.utils.Lists;
import org.reflections.Reflections;
import org.testng.ITestContext;
import org.testng.annotations.DataProvider;

import java.lang.reflect.InvocationTargetException;
import java.util.*;

/**
 * @author xinxin.wu
 * @description: 数据驱动
 * @date 2023/10/18
 * @version: 1.0
 */
@Log4j2
public class TestDataProvider {

    /**
     * CaseHandler集合
     */
    public static Map<String, Class<? extends CaseHandler>> caseHandlers = new HashMap<>();

    /**
     * @param
     * @return void
     * @description: 始化CaseHandler的子类保存到caseHandlers
     */
    public static void setCaseHandlers() {
        try {
            //初始化Reflections工具类，Reflections是一个反射框架
            Reflections reflections = new Reflections("com.example.dztest.apicase.casehandle");
            //获取casehandle目录下CaseHandler.clss的子类
            Set<Class<? extends CaseHandler>> classes = reflections.getSubTypesOf(CaseHandler.class);

            for (Class<? extends CaseHandler> clazz : classes) {
                caseHandlers.put(clazz.getSimpleName(), clazz);
            }
        } catch (Exception err) {
            log.error("CaseHandler 函数解析失败");
            err.printStackTrace();
        }
    }

    /**
     * @description: 获取数据驱动数据
     * @param context
     * @return Iterator<Object>
     */
    @DataProvider(name = "apiCase")
    public static Iterator<Object[]> data(ITestContext context) throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        List<CaseHandler> caseList = new ArrayList<>();

        //从xml测试套文件获取参数
        String case_file = context.getCurrentXmlTest().getParameter("case_file");
        String sheets = context.getCurrentXmlTest().getParameter("sheets");

        String[] case_file_s = case_file.split(".xlsx")[0].split("/");
        String case_file_name = case_file_s[case_file_s.length - 1];
        String[] priority_epic_feature = case_file_name.split("-");
        String epic = priority_epic_feature[1];
        String feature = priority_epic_feature[2];

        List<String> sheetList = new ArrayList<>();
        if ("*".equals(sheets)) {
            //sheets值为*，则取用例文件所有sheet页
            List<ReadSheet> readSheetList = EasyExcel.read(case_file).build().excelExecutor().sheetList();
            for (ReadSheet sheet : readSheetList) {
                sheetList.add(sheet.getSheetName());
            }
        } else {
            String[] sheets_array = sheets.split(",");
            sheetList = Arrays.asList(sheets_array);
        }

        for (String sheet : sheetList) {
            //读取excel用例文件对应sheet页
            CaseFileListener caseFileListener = new CaseFileListener();
            EasyExcel.read(case_file, ApiCaseModel.class, caseFileListener).sheet(sheet).doRead();
            List<ApiCaseModel> apiCaseModelList = Optional.ofNullable(caseFileListener.getApiCaseModelList()).orElse(Lists.newArrayList());

            for (ApiCaseModel apiCaseModel : apiCaseModelList) {
                String a = apiCaseModel.getIsRun();
                //为空则默认不执行
                if (apiCaseModel.getIsRun() == null || apiCaseModel.getIsRun().isEmpty() || apiCaseModel.getIsRun().equals("N")) {
                    continue;
                }

                CaseHandler caseHandler = null;
                if (!caseHandlers.isEmpty() && caseHandlers.containsKey(apiCaseModel.getHandler())) {
                    Class<? extends CaseHandler> aClass = caseHandlers.get(apiCaseModel.getHandler());
                    caseHandler = aClass.getDeclaredConstructor().newInstance();
                } else {
                    caseHandler = new CaseHandler();
                }
                apiCaseModel.setEpic(epic);
                apiCaseModel.setFeature(feature);
                apiCaseModel.setCaseName(sheet);

                caseHandler.setApiCaseModel(apiCaseModel);
                caseList.add(caseHandler);
            }
        }

        List<Object[]> caseListItr = new ArrayList<>();
        for (Object obj : caseList) {
            caseListItr.add(new Object[]{obj});
        }

        return caseListItr.iterator();
    }
}
