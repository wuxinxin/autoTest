package com.example.dztest.service.actions;

import com.example.dztest.service.page.HeaderPage;
import com.example.dztest.service.page.*;
import com.example.dztest.ui.UIDriver;
import com.example.dztest.utils.TimeUtil;
import org.openqa.selenium.By;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 拨号盘
 */
@Service
public class AICallPage {
    @Autowired
    private ICallPage iCallPage;
    @Autowired
    private HeaderPage headerPage;
    @Autowired
    UIDriver uiDriver;

    //切换状态为1“在线”
    public void setStatusOnline() {
        headerPage.clickICallCenter();
        iCallPage.clickCallOut();
        iCallPage.clickOnline();
    }

    //拨打电话
    public void callPhone(String phone) {
        headerPage.clickICallCenter();

        iCallPage.inputPhone(phone);
        iCallPage.startCall();
    }

    /**
     * 坐席状态切换验证
     * 1，坐席登录得助平台，并签入。
     * 2，坐席从“外呼”状态，切换为“小休”。
     * 3，坐席从“小休”状态，切换为“在线”。
     * 4，坐席从“在线”状态，切换为“外呼”。
     */
    public void statusSwitch() {
        headerPage.clickICallCenter();
        this.clickStatus("示忙");
        this.clickToStatus("就餐");

        this.clickStatus("就餐");
        this.clickStatus("示闲");

        this.clickStatus("示闲");
        this.clickStatus("示忙");

        TimeUtil.sleep(1);
    }

    //点击指定状态
    public void clickStatus(String status) {
        TimeUtil.sleep(1);
        uiDriver.getWebDriver().findElement(By.xpath("//div[@class='cfX0Fzt58TjvvsMRW_yS']/span[text()='" + status + "']")).click();
    }

    //切换到指定状态
    public void clickToStatus(String status) {
        TimeUtil.sleep(1);
        uiDriver.getWebDriver().findElement(By.xpath("//div[@class='nsl6myk26SRlZpCCnGwA']/..//span[text()='" + status + "']")).click();
    }

}
