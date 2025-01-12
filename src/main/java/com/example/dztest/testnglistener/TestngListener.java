package com.example.dztest.testnglistener;

import com.example.dztest.service.BaseOfTestCase;
import com.example.dztest.ui.UIDriver;
import io.qameta.allure.Allure;
import io.qameta.allure.Attachment;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import org.testng.TestListenerAdapter;

import java.io.ByteArrayInputStream;



public class TestngListener extends TestListenerAdapter {

    @Override
    public void onTestFailure(ITestResult tr) {
        BaseOfTestCase base = (BaseOfTestCase) tr.getInstance();
        WebDriver webDriver = base.getUiDriver().getWebDriver();
        System.out.println("test");
        byte[] screenshotAs = ((TakesScreenshot) webDriver).getScreenshotAs(OutputType.BYTES);
        Allure.addAttachment("mytest", new ByteArrayInputStream(screenshotAs));
    }

    @Attachment(value = "失败截图如下：",type = "image/png")
    public byte[]  takePhoto(WebDriver driver){
        byte[] screenshotAs = ((TakesScreenshot)driver).getScreenshotAs(OutputType.BYTES);
        return screenshotAs;
    }

    /**
     * 打印测试步骤
     * @param tr
     */
    @Attachment(value = "操作步骤如下：")
    public String logCaseStep(ITestResult tr){
        String step = "1、打开浏览器  2、输入百度地址";
        return step;
    }

    /**
     * 打印测试步骤
     * @param tr
     */
    @Attachment(value = "期望结果如下：")
    public String exceptedResult(ITestResult tr){
        String result = "显示查询结果";
        return result;
    }
}