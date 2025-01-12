package com.example.dztest.service.page;

import com.example.dztest.ui.UIDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CallDialPage extends BasePage {
    @Autowired
    private UIDriver ui;

    //点击拨号盘弹出按钮-->点击后弹出拨号盘
    public void clickIcall() {
        ui.xpath("//SPAN[@class='dzicall-label']").click();
    }

    //点击拨号盘状态切换栏-->点击后弹出状态切换栏
    public void clickIcallStatus() {
        ui.xpath("//div[@class='dzicall-border dzicall-borderButton']").click();
    }

    //拨号盘状态切换栏下，选中“在线”
    public void clickIcallStatusOnline() {
        ui.xpath("//div[@class='dzicall-typeInfo']/span[text()='示闲']").click();
    }

    //拨号盘状态切换栏下，选中“外呼”
    public void clickIcallStatusOutCall() {
        ui.xpath("//SPAN[text()='示忙']").click();
    }

    //拨号盘状态切换栏下，选中“离线”
    public void clickIcallStatusOffline() {
        ui.xpath("//div[@class='dzicall-typeInfo']/span[text()='离线']").click();
    }
}
