package com.example.dztest.service.page;

import com.example.dztest.ui.UIDriver;
import com.example.dztest.utils.uilogger.UILogger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 机器人平台页面类
 */

@Service
public class RobotPlatformPage {
    @Autowired
    public UIDriver uiDriver;

    //点击左侧菜单展开
    public void clickOpen() {
        String xpath = "//div[1]/div[2]/div[1]/img[@class='nav']";
        uiDriver.getWebDriver().findElement(By.xpath(xpath)).click();
    }

    //点击左侧机器人平台
    public void clickRobotPlatform() {
        uiDriver.findBySpanText("机器人平台").click();
    }

    //点击新建[机器人]
    @UILogger(desc = "新建机器人")
    public void clickNew() {
        uiDriver.xpath("//div/button[3][@class='ai-button ai-button--primary ai-button--small']/span").click();
    }

    //是否出现机器人新建弹窗
    @UILogger(desc = "判断是否出现机器人新建弹窗")
    public boolean isRobotNewDialogDisplayed() {
        return uiDriver.getWebDriver().findElement(By.xpath("//span[text()='创建机器人']")).isDisplayed();
    }

    //输入机器人名称
    public void inputRobotName(String content) {
        uiDriver.findByPlaceholder("请输入机器人名称").sendKeys(content);
    }

    //点击机器人应用行业使其展开
    public void clickIndustry() {
        uiDriver.findByPlaceholder("请选择行业").click();
    }

    //在机器人应用行业栏，选择机器人应用行业：电商、金融、制造
    public void chooseIndustry() {//PRB
        String xpath_str = "//div[1]/div[1]/ul/li[1][@class='ai-select-dropdown__item hover']/span";
        WebElement webElement = uiDriver.xpath(xpath_str);
        webElement.click();
    }

    //点击机器人类型
    public void clickRobotType() {
        uiDriver.findByPlaceholder("请选择机器人类型").click();
    }

    //选择机器人类型为：外呼机器人
    public void chooseRobotType() {
        String xpath_str = "//div/ul/li[2][@class='ai-select-dropdown__item hover']";
        uiDriver.getWebDriver().findElement(By.xpath(xpath_str)).click();
    }

    //点击左侧机器人平台
    public void clickConfirm() {
        uiDriver.findBySpanText("确认").click();
        uiDriver.sleep(3);
    }


}
