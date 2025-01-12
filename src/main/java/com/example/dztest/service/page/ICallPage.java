package com.example.dztest.service.page;

import com.example.dztest.ui.UIDriver;
import com.example.dztest.utils.TimeUtil;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.remote.SessionId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;

import java.io.File;

/**
 * 拨号盘
 */
@Service
public class ICallPage extends BasePage {
    private static final Logger logger = LoggerFactory.getLogger(ICallPage.class);

    @Autowired
    private UIDriver ui;

    //坐席状态
    public String getStatus() {
        String status = ui.xpath("//span[@class='dzicall-label']").getText();
        logger.info("当前坐席状态为: " + status);
        return status;
    }

    //是否打开拨号盘
    public boolean showICall() {
        WebElement webElement = ui.getWebDriver().findElement(By.xpath("//div[@class='dzicall-phone-container phone']"));
        return webElement.isDisplayed();
    }

    //循环等待坐席状态出现；循环num次，每次1秒
    public boolean waitStatus(String status, Integer num) {
        String status1 = null;
        for (int i = 0; i < num; i++) {
            try {
                status1 = this.getStatus();
            } catch (Exception e) {
                TimeUtil.sleep(1);
                continue;
            }

            System.out.println("i = " + i + ",坐席状态 = " + status1);
            if (status1.equals(status)) {
                return true;
            }
            TimeUtil.sleep(1);
        }
        return false;
    }


    //点击外呼状态
    public void clickCallOut() {
        ui.getWebDriver().findElement(By.xpath("//div[@class='dzicall-typeInfo']/span[text()='示忙']")).click();
    }

    //点击在线状态
    public void clickOnline() {
        TimeUtil.sleep(1);
        ui.getWebDriver().findElement(By.xpath("//div[@class='dzicall-typeInfo']/span[text()='示闲']")).click();
    }

    //输入电话号码
    public void inputPhone(String content) {
        ui.xpath("//input[@placeholder='请输入需要拨打的电话号码']").sendKeys(content);
    }

    //点击电话号码数字
    public void clickPhoneNum(String num) {
        ui.xpath("//div[text()='" + num + "']").click();
    }

    //拨打电话
    public void startCall() {
        ui.css(".CallCenterbtn_jieqi").click();
    }

    //接听
    public void answerCall() {
        ui.getWebDriver().findElement(By.xpath("//i[@class='dzicall-icon-container dzicall-buttonIcon dzicall-inButtonIcon CallCenterbtn_jieqi']")).click();
    }

    //完成处理
    public void clickFinishAcw() {
        ui.xpathUI("//div[text()='完成处理']").click();
    }

    //挂断
    public void clickEndCall() {
        ui.xpathUI("//div[@class='dzicall-button-container dzicall-button dzicall-hangupButton']").click();
    }

    //挂断
    public void endCall() {
        ui.css(".CallCenterbtn_guaduan").click();
    }

    //切换到历史记录
    public void switchHistory() {
        ui.css(".CallCenterbtn_jilu_pre").click();
    }

    //拨打历史电话
    public void callPhoneHistory(int index) {
        ui.css("div[title='拨打电话']").click();
    }


    //客户姓名
    public String customerName() {
        return uiDriver.getWebDriver().findElement(By.xpath("//div[@class='dzicall-infoContainer']/div[@class='dzicall-name']")).getText();

    }

    //客户号码
    public String customerPhone() {
        return uiDriver.getWebDriver().findElement(By.xpath("//div[@class='dzicall-infoContainer']/div[@class='dzicall-info']/div[1]")).getText();
    }

    //客户城市
    public String customerCity() {
//        return ui.css("[class^='icall_phoneNumberAddress_'] > div").getText();
        return uiDriver.getWebDriver().findElement(By.xpath("//div[@class='dzicall-infoContainer']/div[@class='dzicall-info']/div[@class='dzicall-phoneNumberAddress']")).getText();
    }

    //运营商
    public String customerRemark() {
//        return ui.css("[class^='icall_phoneNumberRemark_'] > div").getText();
        return uiDriver.getWebDriver().findElement(By.xpath("//div[@class='dzicall-infoContainer']/div[@class='dzicall-info']/div[@class='dzicall-phoneNumberRemark']")).getText();
    }

    //振铃状态
    public String callMessage() {
        return ui.css("[class^='icall_message_'] > div").getText();
    }

    //弹开通话中拨号盘
    public void clickOnCallingPage() {
        uiDriver.getWebDriver().findElement(By.xpath("//div[@class='dzicall-info']/span[@class='dzicall-label']")).click();
    }

    //花后点击“完成处理”
    public void clickDoneAfterCalling() {
        uiDriver.getWebDriver().findElement(By.xpath("//div[@class='fWZfY1ZgW64naHWS7J39']/div[@class='yhxQQ3JV723F3BCflPFl OVRnBLhML8B3mnE8EUL4 XJ_m4D5LNq5uRAmlkuye']")).click();
    }

    //拨号盘点击转接、咨询、会议、协助
    public void clickButton(String text) {
        uiDriver.getWebDriver().findElement(By.xpath("//div[@class='dzicall-label' and text()='" + text + "']")).click();
    }

    //选中技能组
    public void clickSkillGroup(String text) {
        uiDriver.getWebDriver().findElement(By.xpath("//span[contains(text(), \"" + text + "\") and @class='dzicall-label']")).click();
    }

    //点击转接按键,如转接并挂断按键
    public void clickSwitch(String text) {
        uiDriver.getWebDriver().findElement(By.xpath("//span[text()='" + text + "']")).click();
    }

}
