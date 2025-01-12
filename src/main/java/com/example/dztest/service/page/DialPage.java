package com.example.dztest.service.page;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class DialPage extends BasePage {
    private WebDriver driver;

    //初始化工作
    public DialPage(WebDriver driver) { //构造方法没有void
        this.driver = uiDriver.getWebDriver();
    }

    //点击拨号盘弹出按钮-->点击后弹出拨号盘
    public void clickIcall() {
        driver.findElement(By.xpath("//SPAN[@class='icall_label__RNwX']")).click();
    }

    //点击拨号盘状态切换栏-->点击后弹出状态切换栏
    public void clickIcallStatus() {
        driver.findElement(By.xpath("//i[@class='icall_container_2qUf0 CallCenterarrow_down icall_listArrow_10um_']")).click();
    }

    //拨号盘状态切换栏下，选中“在线”
    public void clickIcallStatusOnline() {
        driver.findElement(By.xpath("//SPAN[text()='在线']")).click();
    }
}
