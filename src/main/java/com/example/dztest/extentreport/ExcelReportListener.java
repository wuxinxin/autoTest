package com.example.dztest.extentreport;

import com.alibaba.excel.EasyExcel;
import com.example.dztest.apicase.CaseHandler;
import com.example.dztest.apicase.model.ApiCaseModel;
import com.example.dztest.config.DzConfig;
import com.example.dztest.extentreport.model.CaseResultModel;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.testng.*;
import org.testng.xml.XmlSuite;

import javax.annotation.Resource;
import java.util.*;


public class ExcelReportListener implements IReporter {
    /**
     * 生成的路径以及文件名
     */
    private static final String OUTPUT_FOLDER = "test-output/";

    /**
     * 测试用例执行结果
     */
    private ArrayList<CaseResultModel> caseResultModels = new ArrayList<>();

    @Value("${database.url}")
    private String url;

    @Resource
    private Environment environment;

    /**
     *将IResultMap对象转换为ITestResult集合
     * @param resultMap 用例执行结果map对象
     * @return ITestResult集合
     */
    private ArrayList<ITestResult> listTestResult(IResultMap resultMap) {
        Set<ITestResult> results = resultMap.getAllResults();
        return new ArrayList<ITestResult>(results);
    }

    /**
     * 对测试结果集合中的元素按开始时间排序
     * @param list 测试结果集合
     */
    private void sort(List<ITestResult> list) {
        Collections.sort(list, new Comparator<ITestResult>() {
            @Override
            public int compare(ITestResult r1, ITestResult r2) {
                if (r1.getStartMillis() > r2.getStartMillis()) {
                    return 1;
                } else {
                    return -1;
                }
            }
        });
    }

    /**
     * 将用例数字状态转换为对应的字符串状态；1-pass，2-failed，3-skip
     * @param status
     * @return 用例结果状态
     */
    private String getStatus(int status) {
        String statusString = null;
        switch (status) {
            case 1:
                statusString = "pass";
                break;
            case 2:
                statusString = "failed";
                break;
            case 3:
                statusString = "skip";
                break;
            default:
                break;
        }
        return statusString;
    }

    /**
     * 将测试结果集合写入excel
     * @param caseResultModels 测试结果集合
     * @param path  输出路径
     */
    private void outputResult(ArrayList<CaseResultModel> caseResultModels, String path) {
        EasyExcel.write(path, CaseResultModel.class).sheet().doWrite(caseResultModels);
    }

    /**
     * 将ArrayList<ITestResult>集合转换为ArrayList<CaseResultModel>集合
     * @param iTestResults 测试结果集合
     */
    private void testToCase(ArrayList<ITestResult> iTestResults) {
        for (ITestResult iTestResult : iTestResults) {
            //类名
            String className = iTestResult.getTestClass().getName();
            if ("com.example.dztest.testcase.apiCase.myApiCase".equals(className)) {
                //v2版本用例
                Object[] parameters = iTestResult.getParameters();
                CaseHandler caseHandler = (CaseHandler) parameters[0];
                ApiCaseModel apiCaseModel = caseHandler.getApiCaseModel();
                //TODO v2版本用例结果写入excel

            } else {
                //v1版本用例
                //[优先级]产品-模块;如 [p1]基础平台-客户中心/客户公海
                String product = "";
                String module = "";
                String suiteName = iTestResult.getTestContext().getSuite().getName();
                String[] strings = suiteName.split("-");
                try {
                    product = strings[0].split("]")[1];
                    module = strings[1];
                } catch (Exception exception) {
                    System.out.println(exception);
                }

                //测试名称-->测试套
                String testName = iTestResult.getTestContext().getName();
                //方法描述-->测试用例名称|作者;如新增意向客户|吴新鑫
                String[] desc = iTestResult.getMethod().getDescription().split("\\|");
                String caseName = "";
                String author = "";
                try {
                    caseName = desc[0];
                    author = desc[1];
                } catch (Exception exception) {
                    System.out.println(exception);
                }
                //执行结果
                int result = iTestResult.getStatus();

                CaseResultModel caseResultModel = new CaseResultModel();
                caseResultModel.setProduct(product);
                caseResultModel.setVersion(DzConfig.version);
                caseResultModel.setModule(module);
                caseResultModel.setSuite(testName);
                caseResultModel.setCaseName(caseName);
                caseResultModel.setAuthor(author);
                caseResultModel.setStatus(this.getStatus(result));
                this.caseResultModels.add(caseResultModel);
            }
        }
    }


    @Override
    public void generateReport(List<XmlSuite> xmlSuites, List<ISuite> suites, String outputDirectory) {
        ArrayList<ITestResult> iTestResults = new ArrayList<ITestResult>();
        //遍历测试套
        for (ISuite iSuite : suites) {
            //测试套名称
            String suiteName = iSuite.getName();

            Map<String, ISuiteResult> iSuiteResultMap = iSuite.getResults();
            //遍历test
            for (ISuiteResult iSuiteResult : iSuiteResultMap.values()) {
                ITestContext iTestContext = iSuiteResult.getTestContext();
                //成功的用例
                IResultMap passedTests = iTestContext.getPassedTests();
                //失败的用例
                IResultMap failedTests = iTestContext.getFailedTests();
                //跳过的用例
                IResultMap skippedTests = iTestContext.getSkippedTests();

                iTestResults.addAll(this.listTestResult(passedTests));
                iTestResults.addAll(this.listTestResult(failedTests));
                iTestResults.addAll(this.listTestResult(skippedTests));
            }
        }

        this.sort(iTestResults);
        this.testToCase(iTestResults);
        this.outputResult(this.caseResultModels, outputDirectory + "/CaseOutResult.xlsx");

        //结果同步到数据库
        if ("mysql".equals(DzConfig.out)) {
            this.caseToDB(this.caseResultModels);
        }
    }

    /**
     * 同步测试用例执行结果到数据库
     * @param caseResultModels
     */
    public void caseToDB(ArrayList<CaseResultModel> caseResultModels) {

    }
}