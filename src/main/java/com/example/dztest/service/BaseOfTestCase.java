package com.example.dztest.service;

import cn.hutool.core.util.StrUtil;
import com.example.dztest.common.annotations.MValue;
import com.example.dztest.domain.GlobalVar;
import com.example.dztest.service.actions.AICallPage;
import com.example.dztest.service.actions.ALoginPage;
import com.example.dztest.service.api_action.ABasicApi;
import com.example.dztest.service.interfaces.basic.BasicApi;
import com.example.dztest.service.interfaces.speech.AgentApi;
import com.example.dztest.service.interfaces.speech.UserCommunicateApi;
import com.example.dztest.service.interfaces.uss.UssApi;
import com.example.dztest.service.page.CallInfoPage;
import com.example.dztest.service.page.*;
import com.example.dztest.service.page.DesktopPage;
import com.example.dztest.service.page.iframe.DesktopCallOutIFrame;
import com.example.dztest.service.page.iframe.DesktopOnLineIFrame;
import com.example.dztest.service.remote.RemoteSipp;
import com.example.dztest.ui.UIDriver;
import com.example.dztest.utils.SpringContextUtil;
import com.example.dztest.utils.common.Delayer;
import com.example.dztest.utils.excel.base.AnnotationUtils;
import com.example.dztest.utils.excel.exception.ExcelConfigException;
import com.example.dztest.utils.excel.plcy.*;
import com.example.dztest.utils.screen_shot.ScreenShotUtils;
import com.example.dztest.utils.screen_shot.UIScreenShotByException;
import com.example.dztest.utils.uilogger.U2IAnno;
import io.qameta.allure.Allure;
import org.apache.commons.io.FileUtils;
import org.joda.time.DateTime;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.remote.SessionId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.IHookCallBack;
import org.testng.ITestResult;
import org.testng.annotations.*;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.*;

/**
 * @ClassName: BaseCase
 * @description: Base case of testcase and page in the way of testNG
 * Extends TestNgBaseCase,you can handle exception,transaction,take screen shot and so on
 * @author: jian.ma@msxf.com
 * @create: 2021/11/18
 * @update: 2022/07/01
 **/


public class BaseOfTestCase extends AbstractTestNGSpringContextTests implements Delayer, U2IAnno, Base {
    protected final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Value("${screen.all:false}")
    private String screenMode;

    @Value("${excel.read.mode:column}")
    private String excelReadMode;

    @Autowired
    protected UIDriver uiDriver;

    public static int stepCount = 0;

    @Autowired
    protected UserCommunicateApi userCommunicateApi;

    @Autowired
    protected UssApi ussApi;

    @Autowired
    protected BasicApi basicApi;

    @Autowired
    protected ABasicApi aBasicApi;

    @Autowired
    protected ALoginPage aLoginPage;

    @Autowired
    protected AICallPage aiCallPage;

    @Autowired
    protected RemoteSipp remoteSipp;

    @Autowired
    protected CallInfoPage callInfoPage;

    @Autowired
    protected ICallPage iCallPage;

    @Autowired
    protected DesktopPage desktopPage;

    @Autowired
    protected DesktopOnLineIFrame desktopOnLineIFrame;

    @Autowired
    protected DesktopCallOutIFrame desktopCallOutIFrame;

    @Autowired
    protected AgentApi agentApi;

    @Autowired
    protected HeaderPage headerPage;

    private WebDriver webDriver;

    public UIDriver getUiDriver() {
        return uiDriver;
    }

    @BeforeClass
    public void clsLevelInit() {
        stepCount = 0;

        /**
         * if your env is stest3/stest4/online,then your excel name ,maybe,should be as follow:
         * stest3.xlsx/stest4.xlsx/online.xlsx like this,and this excel
         * file need to put in path:resources/excel/...
         *
         * and you need to sure your data config mode,specifically =>
         * if column mode way,config it as follow in excel:
         * ----------------------------------
         * |key     | |value              |
         * ----------------------------------
         * username | jian.ma@msxf.com ...
         * ----------------------------------
         * password | 123456 ...
         * ----------------------------------
         * code     | 111111 ...
         * ----------------------------------
         *===================================================
         * if row mode way,config it as follow in excel:
         * --------------------------------------------------
         * |key     | username              password   code
         * --------------------------------------------------
         * |value   | jian.ma@msxf.com      123456     111111
         * --------------------------------------------------
         *
         * notes:
         * one of the mode can not  exist side by side
         */
        Field[] declaredFields = this.getClass().getDeclaredFields();

        if (AnnotationUtils.isMValue(declaredFields)) {
            try {
                Map<String, ExcelPlcy> beansOfType = SpringContextUtil.getApplicationContext().getBeansOfType(ExcelPlcy.class);

                beansOfType.entrySet().stream()
                        .filter(k -> k.getValue().equals(this.excelReadMode))
                        .map(Map.Entry::getValue)
                        .findFirst()
                        .orElse(new ColumnExec())
                        .exec();

                this.initBasicData(declaredFields);
            } catch (Exception e) {
                logger.error("Exception happened: " + e);
                throw new ExcelConfigException(e);
            }


        }
    }

    @BeforeMethod
    public void mtdLevelInit() {
        stepCount = 0;
    }

    @AfterMethod
    public void mtdLevelDestructor() {
        logger.info("==> In " + this.getClass() + ", afterMethod");

        stepCount = 0;
        uiDriver.quitAll();
    }

    @Override
    public void run(IHookCallBack callBack, ITestResult testResult) {
        Method testMethod = testResult.getMethod().getConstructorOrMethod().getMethod();
        UIScreenShotByException annotation = testMethod.getAnnotation(UIScreenShotByException.class);
        logger.info("getAnnotation==>>>" + testMethod.getAnnotation(UIScreenShotByException.class));

        //====run start====
        try {
            super.run(callBack, testResult);
            //callBack.runTestMethod(testResult);
        } catch (Throwable throwable) {
            logger.info("In TestNgBaseCase catch: {}", throwable);
            webDriver = uiDriver.getWebDriver();

            String fileName = this.getStaticPath() + testMethod.getName() + this.getDateTime() + ".png";
            logger.info("Generating fileName is: " + fileName);

            Boolean isNeedScreenshot = false;

            if (null != webDriver) {
                if (!(boolean) uiDriver.getIsRemote()) {
                    RemoteWebDriver remoteWebDriver = (RemoteWebDriver) webDriver;
                    SessionId sessionId = remoteWebDriver.getSessionId();
                    if (sessionId != null) {
                        isNeedScreenshot = true;

                        //remain the way of screen shot when config is false,
                        //it means screen shot when exception
                        //handle the question:Session ID is null. Using WebDriver after calling quit()?
                        if (isNeedScreenshot && "false".equals(screenMode)) {
                            ScreenShotUtils.takeScreenshot(webDriver, (screeShot) -> {
                                logger.info("driver is present !!!" + webDriver);
                                File srcFile = ((TakesScreenshot) webDriver).getScreenshotAs(OutputType.FILE);
                                byte[] screenshotAs = ((TakesScreenshot) webDriver).getScreenshotAs(OutputType.BYTES);
                                try {
                                    FileUtils.copyFile(srcFile, new File(fileName));
                                    this.takePhotoToAllureReport(testMethod.getName(), screenshotAs);
                                } catch (Exception ex) {
                                    logger.error("FileUtils.copyFile Exception: {}", ex);
                                }
                                return null;
                            });
                        }
                    }
                }
            }
        }
        //====end run start====
    }

    private static String getStringRemoveSuffix2Prefix(String str) {
        String temp = str;

        temp = StrUtil.removeSuffix(StrUtil.removePrefix(temp, "${"), "}");
        return temp;
    }

    /**
     * auto set value of property with annotation @MValue
     */
    private void initBasicData(Field[] declaredFields) throws IllegalAccessException {
        /**
         * use: get the key value from excel
         *     @MValue("${basic.username}")
         *     private String userName;
         *
         *     @MValue("${uss.password}")
         *     private String passWord;
         *
         *     @MValue("${basic.majian.url}")
         *     private String url;
         */
        Set set = new HashSet();

        for (int i = 0; i < GlobalVar.BASE_DATA.size(); i++) {
            set.add(GlobalVar.BASE_DATA.get(i).keySet().iterator().next());
        }

        for (Field declaredField : declaredFields) {
            declaredField.setAccessible(true);

            if (declaredField.isAnnotationPresent(MValue.class)) {
                MValue myAnnotation = declaredField.getAnnotation(MValue.class);
                String vk = getStringRemoveSuffix2Prefix(myAnnotation.value());

                if (!set.contains(vk)) {
                    throw new ExcelConfigException(vk + " key not exist in scripts or excel,please check");
                }

                for (int i = 0; i < GlobalVar.BASE_DATA.size(); i++) {
                    Iterator<String> iterator = GlobalVar.BASE_DATA.get(i).keySet().iterator();
                    String key = iterator.next();

                    if (vk.equals(key)) {
                        Object vv = GlobalVar.BASE_DATA.get(i).get(vk).toString();
                        String typeName = declaredField.getType().getTypeName();

                        if ("java.lang.Integer".equals(typeName)) {
                            vv = Integer.valueOf(vv.toString());
                        }
                        //handle the other type
                        //..

                        //set the value
                        declaredField.set(this, vv);
                        break;
                    }
                }
            }
        }
    }

    public static String getDateTime() {
        DateTime currentDateTime = new DateTime();
        return currentDateTime.toString("YYYY-MM-dd-hh-mm-ss");
    }

    public static String getStaticPath() {
        String path = "";
        String winPath = System.getProperty("user.dir")
                + "\\src\\main\\resources\\static\\screen_shot\\";
        String linuxPath = System.getProperty("user.dir")
                + "/src/main/resources/static/screen_shot/";
        boolean isWin = System.getProperty("os.name").toLowerCase().contains("win");

        return (true == isWin) ? winPath : linuxPath;
    }


    /**
     * @param name:         current driver
     * @param screenshotAs: the byte when exception occurs
     */
    public static void takePhotoToAllureReport(String name, byte[] screenshotAs) {
        Allure.addAttachment(name, new ByteArrayInputStream(screenshotAs));
    }

    public static void logStep(String stepInfo) {
        stepCount++;
        System.out.println("stepCount = " + stepCount);
        Allure.attachment("[步骤" + stepCount + "]" + stepInfo, stepInfo);
    }
}