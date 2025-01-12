package com.example.dztest.utils;

import com.example.dztest.service.BaseOfTestCase;
import com.example.dztest.ui.UIDriver;
import com.example.dztest.utils.screen_shot.ScreenShotUtils;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;

@Component
public class OverScreen {
    public static final Logger logger = LoggerFactory.getLogger(OverScreen.class);

    @Autowired
    private UIDriver uiDriver;

    private WebDriver webDriver;

    public  void commonScreen() {
        uiDriver.open("http://10.99.67.70:8080/job/AutoTest/allure/");
        webDriver = uiDriver.getWebDriver();
        String fileName = BaseOfTestCase.getStaticPath() + "data_overview.png";


        ScreenShotUtils.takeScreenshot(webDriver, (screeShot) -> {
            File srcFile = ((TakesScreenshot) webDriver).getScreenshotAs(OutputType.FILE);
            byte[] screenshotAs = ((TakesScreenshot) webDriver).getScreenshotAs(OutputType.BYTES);
            try {
                FileUtils.copyFile(srcFile, new File(fileName));
                BaseOfTestCase.takePhotoToAllureReport(fileName, screenshotAs);
            } catch (Exception ex) {
                logger.error("FileUtils.copyFile Exception: {}", ex);
            }
            return null;
        });
        uiDriver.quitAll();
    }
}
