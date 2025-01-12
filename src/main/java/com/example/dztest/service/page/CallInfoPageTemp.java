package com.example.dztest.service.page;

import com.example.dztest.ui.UIDriver;
import com.example.dztest.utils.screen_shot.UIScreenShotByException;
import com.example.dztest.utils.uilogger.UILogger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 通话信息
 * 测试引用page类
 */

@Service
public class CallInfoPageTemp {
    @Autowired
    public UIDriver uiDriver;

    @UILogger(module = "测试页面page", desc = "调用参数测试page接口", isPage = true)
    public void testArgs(String desc) {
        System.out.println("desc = " + desc);
    }

    @UILogger(module = "测试页面page", desc = "调用goToTestWeb", isPage = true)
    public void goToTestWeb() {
        WebDriver tempDriver = uiDriver.getWebDriver();
        System.out.println("which driver============>" + tempDriver);
    }

    //可见 弹屏页
    public boolean showCallInfo() {
        uiDriver.switch_to_default_content();
        //切换到online iframe
        WebElement oneline_iframe = uiDriver.getWebDriver().findElement(By.xpath("//iframe[@id='online']"));
        uiDriver.getWebDriver().switchTo().frame(oneline_iframe);
        WebElement webElement = uiDriver.getWebDriver().findElement(By.xpath("//iframe[contains(@src, '/speech/front/main/callingIframe')]"));
        boolean styple = webElement.isDisplayed();
        uiDriver.switch_to_default_content();
        return styple;
    }

    //切换到speech iframe
    @UILogger(module = "测试页面page", desc = "切换到speech iframe", isPage = true)
    public void switchSpeechFrame() {
        //切换到online iframe
        uiDriver.switchToByXpath("//iframe[@id='online']");
        //切换到speech iframe
        uiDriver.switchToByXpath("//iframe[contains(@src, '/speech/front/main/callingIframe')]");
    }


    //导航 进入 通话信息 页
    public void clickTab() {
        uiDriver.findBySpanContainsText("通话信息").click();
    }

    //选择 通话小结 两级
    public void selectCallSummary2(String first, String second) {
        uiDriver.getWebDriver().findElement(By.xpath("//span[@class='ai-cascader__label']")).click();
//        uiDriver.findByPlaceholder("请选择通话小结").click();
        uiDriver.findByLiText(first).click();
        if (second != null) uiDriver.findByLiText(second).click();
    }

    //选择 通话小结 一级
    public void selectCallSummary1(String first) {
        selectCallSummary2(first, null);
    }

    //输入 服务备注
    public void inputRemarks(String content) {
        uiDriver.xpath("//label[text()='业务小结']/../..//textarea").sendKeys(content);
    }

    //可见 完结 按钮
    public Boolean showEnd() {
        WebElement webElement = uiDriver.getWebDriver().findElement(By.xpath("//label[text()='业务小结']/../../..//span[text()='完结']/.."));
        return webElement.isDisplayed();
    }

    //可见 完结&修改状态为在线  按钮
    public Boolean showEndAndUpdate() {
        WebElement webElement = uiDriver.getWebDriver().findElement(By.xpath("//label[text()='业务小结']/../../..//span[text()='完结&修改状态为在线']/.."));
        return webElement.isDisplayed();
    }

    //点击 完结
    public void clickEnd() {
        uiDriver.xpath("//label[text()='业务小结']/../../..//span[text()='完结']").click();
    }

    //点击 完结
    public void clickEndAndUpdate() {
        uiDriver.xpath("//label[text()='业务小结']/../../..//span[text()='完结&修改状态为在线']").click();
    }

    //点击 暂存
    public void clickCache() {
        uiDriver.xpath("//label[text()='业务小结']/../../..//span[text()='暂存']").click();
    }

    //点击 业务记录
    public void clickWorkNotes() {
        uiDriver.xpath("//div[text()='业务记录']").click();
    }

    //名单资料页
    //点击 名单资料
    public void clickListData() {
        uiDriver.xpath("//div[text()='名单资料']").click();
    }

    //得到客户姓名
    public String getName() {
        return uiDriver.xpath("//label[@for='customerName']/../div/div/input").getAttribute("value");
    }

    //得到客户号码
    public String getNum() {
        return uiDriver.xpath("//label[@for='phoneNumber']/../div/div/input").getAttribute("value");
    }

    //得到备注
    public String getRemarks() {
        return uiDriver.xpath("//label[@for='customerRemark']/../div/div/textarea").getAttribute("value");
    }

    //修改备注
    public void sendRemarks(String remark) {
        WebElement element = uiDriver.xpath("//label[@for='customerRemark']/../div/div/textarea");
        element.clear();
        element.sendKeys(remark);
    }

    //点击保存
    public void clickListSave() {
        uiDriver.xpath("//label[@for='customerRemark']/../..//span[text()='保存']").click();
    }


    //手动外呼任务，外呼判断呼叫弹屏
    public boolean showCallDialog() {
        return uiDriver.getWebDriver().findElement(By.xpath("//span[text()='呼叫弹屏']")).isDisplayed();
    }
}
