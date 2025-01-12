package com.example.dztest.extentreport;

import com.example.dztest.apicase.CaseHandler;
import com.example.dztest.apicase.model.ApiCaseModel;
import com.example.dztest.apicase.utils.BeanContextUtils;
import com.example.dztest.config.DzConfig;
import com.example.dztest.extentreport.db.dao.CaseInfoMapper;
import com.example.dztest.extentreport.db.dao.CaseTaskMapper;
import com.example.dztest.extentreport.db.domain.CaseInfo;
import com.example.dztest.extentreport.db.domain.CaseTask;
import com.example.dztest.feishu.FeishuClient;
import lombok.SneakyThrows;
import org.testng.*;
import org.testng.xml.XmlSuite;

import java.math.BigDecimal;
import java.util.*;

/**
 * 测试结果同步到数据库-监听类
 */
public class DbReportListener implements IReporter {

    //IReporter实现类中引用注册的spring bean，使用下面方式
//    private CaseInfoMapper caseInfoMapper = BeanContextUtils.getBean(CaseInfoMapper.class);
//
//    private CaseTaskMapper caseTaskMapper = BeanContextUtils.getBean(CaseTaskMapper.class);

    /**
     * 测试任务
     */
    private CaseTask caseTask = new CaseTask();

    /**
     * 测试用例信息
     */
    private ArrayList<CaseInfo> caseInfos = new ArrayList<>();

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
     * 将ArrayList<ITestResult>集合转换为ArrayList<CaseResultModel>集合
     * @param iTestResults 测试结果集合
     */
    private void testToCase(ArrayList<ITestResult> iTestResults) {
        for (ITestResult iTestResult : iTestResults) {
            //执行结果
            int result = iTestResult.getStatus();
            CaseInfo caseInfo = new CaseInfo();
            caseInfo.setResult(Byte.valueOf((byte) result));
            Long startTime = iTestResult.getStartMillis();
            Long endTime = iTestResult.getEndMillis();
            caseInfo.setStartTime(new Date(startTime));
            caseInfo.setEndTime(new Date(endTime));
            caseInfo.setDzVersion(DzConfig.version);
            if ("mysql".equals(DzConfig.out)) {
                caseInfo.setTaskId(Long.valueOf(this.caseTask.getId()));
            }

            //类名
            String className = iTestResult.getTestClass().getName();
            if ("com.example.dztest.testcase.apiCase.myApiCase".equals(className)) {
                //v2版本用例
                Object[] parameters = iTestResult.getParameters();
                CaseHandler caseHandler = (CaseHandler) parameters[0];
                ApiCaseModel apiCaseModel = caseHandler.getApiCaseModel();

                caseInfo.setCaseName(apiCaseModel.getCaseName());
                caseInfo.setCaseType("v2");
                caseInfo.setProduct(apiCaseModel.getEpic());
                caseInfo.setModule(apiCaseModel.getFeature());
                caseInfo.setAuth(apiCaseModel.getAuthor());
                caseInfo.setCaseStep(apiCaseModel.getSteps());
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
                String caseName = null;
                String author = null;
                try {
                    caseName = desc[0];
                    author = desc[1];
                } catch (Exception exception) {
                    System.out.println(exception);
                }

                caseInfo.setCaseName(caseName);
                caseInfo.setProduct(product);
                caseInfo.setModule(module);
                caseInfo.setCaseType("v1");
                caseInfo.setAuth(author);
            }
            this.caseInfos.add(caseInfo);
        }
    }


    @SneakyThrows
    @Override
    public void generateReport(List<XmlSuite> xmlSuites, List<ISuite> suites, String outputDirectory) {
        if (DzConfig.out.isEmpty()) {
            return;
        }

        //统计此次任务成功、失败、跳过的总用例数
        int taskFailSize = 0;
        int taskPassSize = 0;
        int taskSkipSize = 0;

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

                taskPassSize = taskPassSize + passedTests.size();
                taskFailSize = taskFailSize + failedTests.size();
                taskSkipSize = taskSkipSize + skippedTests.size();

                iTestResults.addAll(this.listTestResult(passedTests));
                iTestResults.addAll(this.listTestResult(failedTests));
                iTestResults.addAll(this.listTestResult(skippedTests));
            }
        }
        //按开始时间排序
        this.sort(iTestResults);

        int sum = taskFailSize + taskSkipSize + taskPassSize;
        this.caseTask.setCaseNum(sum);
        BigDecimal successRate = new BigDecimal((taskPassSize * 100) / sum).setScale(2, BigDecimal.ROUND_HALF_UP);
        this.caseTask.setSuccessRate(successRate);
        this.caseTask.setCaseSuccessNum(taskPassSize);
        this.caseTask.setCaseSkippedNum(taskSkipSize);
        this.caseTask.setCaseFailedNum(taskFailSize);
        this.caseTask.setDzVersion(DzConfig.version);
        this.caseTask.setRunEnv(DzConfig.env);
        this.caseTask.setBranch(DzConfig.branch);
        if (!DzConfig.buildUrl.isEmpty()) {
            this.caseTask.setTestReport(DzConfig.buildUrl + "allure");
        }

        //第一条用例开始时间，定义为任务开始时间；最后一条用例结束时间，定义为任务结束时间。
        Long taskStartTime = iTestResults.get(0).getStartMillis();
        Long taskEndTime = iTestResults.get(iTestResults.size() - 1).getEndMillis();
        this.caseTask.setStartTime(new Date(taskStartTime));
        this.caseTask.setEndTime(new Date(taskEndTime));

        if ("mysql".equals(DzConfig.out)) {
            CaseTaskMapper caseTaskMapper = BeanContextUtils.getBean(CaseTaskMapper.class);
            caseTaskMapper.insertSelective(this.caseTask);
        }
        this.testToCase(iTestResults);

        if ("mysql".equals(DzConfig.out)) {
            this.caseInfoToMysql(this.caseInfos);
        } else if ("feishu".equals(DzConfig.out)) {
            this.caseInfoToFeishu(this.caseTask, this.caseInfos);
        }

//        this.caseToDB(this.caseTask, this.caseInfos);
    }

    /**
     * 任务用例数据同步到mysql
     * @param caseInfos
     */
    public void caseInfoToMysql(ArrayList<CaseInfo> caseInfos) {
        CaseInfoMapper caseInfoMapper = BeanContextUtils.getBean(CaseInfoMapper.class);
        caseInfoMapper.batchInsert(caseInfos);
    }

    /**
     * 任务用例数据同步到飞书
     * @param caseInfos
     */
    public void caseInfoToFeishu(CaseTask caseTask, ArrayList<CaseInfo> caseInfos) throws Exception {
        FeishuClient feishuClient = new FeishuClient();
        //dz版本号
        String title = caseTask.getDzVersion();
        //查询版本号节点
        String objToken = feishuClient.getNodeChild(title);
        //初始化token
        feishuClient.initAppAcessToken();
        if (objToken == null) {
            //不存在版本号节点，则创建版本号节点
            objToken = feishuClient.createNode(title);
            //重命名默认工作表名称，并写入首行数据；“Sheet1”-->"任务"
            feishuClient.updateSheet(objToken);
        }
        //获取要新建“用例”工作表的序号
        feishuClient.getCaseSheetName(objToken);
        //“任务”表插入任务信息；
        feishuClient.addCaseTask(objToken, caseTask);
        // 新建"用例"工作表，并插入用例数据
        feishuClient.addSheetCaseInfo(objToken, caseInfos);
    }
}