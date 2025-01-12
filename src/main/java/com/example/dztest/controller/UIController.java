package com.example.dztest.controller;

import com.example.dztest.domain.TestCase;
import com.example.dztest.service.actions.*;
import com.example.dztest.service.actions.ALoginPage;
import com.example.dztest.utils.ChromeDriverUtil;
import com.example.dztest.utils.ExcelUtil;
import org.openqa.selenium.WebDriver;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

@RestController
public class UIController {
    private static  List<WebDriver> webDriverList =  new ArrayList<>();

    @PostMapping(value = "/ui/quit")
    public void quit(){
        System.out.println("标题："+ webDriverList);
        webDriverList.parallelStream().forEach(webDriver -> {
            webDriver.quit();
        });
        System.out.println("标题："+ webDriverList);
    }

    @PostMapping(value = "/ui/login/upload", consumes = "multipart/form-data")
    public void uploadLoginList(@RequestParam("file") MultipartFile file){
        List<TestCase> testCaseList= ExcelUtil.readFile(file, "Sheet1", TestCase.class);
        //多线程并发
        testCaseList.parallelStream().forEach(testCase ->{
            String node = testCase.getNode();
            String remoteUrl = "http://" + node + ":5555/wd/hub";
            System.out.println(remoteUrl);
            WebDriver webDriver = ChromeDriverUtil.initRemoteChromeDriver(remoteUrl);
            String account = testCase.getLoginAccount();
            String pwd = testCase.getLoginPwd();
            webDriverList.add(webDriver);
            webDriver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
            webDriver.get("https://stest3https.zkj.test");
            System.out.println("标题："+ webDriver.getTitle());

            ALoginPage aLoginPage = new ALoginPage();
            aLoginPage.login(account, pwd);

            ADialPage aDialPage = new ADialPage(webDriver);
            aDialPage.setStatusOnline();

            System.out.println("标题："+ webDriver.getTitle());
            System.out.println("标题："+ webDriverList);
        }
        );
    }

    @PostMapping(value = "/ui/web", consumes = "multipart/form-data")
    public void uiweb(@RequestParam("file") MultipartFile file){
        List<TestCase> testCaseList= ExcelUtil.readFile(file, "Sheet1", TestCase.class);
        //多线程并发
        testCaseList.parallelStream().forEach(testCase ->{
                    String node = testCase.getNode();
                    String remoteUrl = "http://" + node + ":5555/wd/hub";
                    System.out.println(remoteUrl);
                    WebDriver webDriver = ChromeDriverUtil.initRemoteChromeDriver(remoteUrl);

                    webDriver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
                    webDriver.get("https://u-xinxin01.dezhubot.com/vChat?token=qYJFVb32Ejy2&showChat=true&layout=true");
                    System.out.println("标题："+ webDriver.getTitle());

                    System.out.println("标题："+ webDriver.getTitle());

                }
        );
    }
}
