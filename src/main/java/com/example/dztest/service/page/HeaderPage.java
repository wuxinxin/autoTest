package com.example.dztest.service.page;

import com.example.dztest.ui.UIDriver;
import com.example.dztest.utils.TimeUtil;
import org.openqa.selenium.By;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class HeaderPage extends BasePage {
    @Autowired
    private UIDriver ui;

    //打开拨号盘
    public void clickICallCenter() {
        uiDriver.getWebDriver().findElement(By.xpath("//div[@class='dzicall-info']/span[text()='示忙']")).click();
    }

    //点击在线客服状态下拉栏
    public void clickOnline() {
        TimeUtil.sleep(1);
        uiDriver.getWebDriver().findElement(By.xpath("//a[@class='current-title el-popover__reference']//span[text()='离线']")).click();
    }

    //点击在线客服状态下拉栏--需入参当时的状态
    public void clickOnline(String status) {
        TimeUtil.sleep(1);
        uiDriver.getWebDriver().findElement(By.xpath("//a[@class='current-title el-popover__reference']//span[text()='" + status + "']")).click();
    }

    //在线客服状态栏，选择状态: 接线、小休、离线、挂起
    public void clickOnlineStatus(String status) {
//        String xpath_str = "//body[@class='dz']/div[@class='ai-popover ai-popper new-2-0-ai-ui-header-customer-content']/div[@title='" + status + "']/span";
        String xpath_str = "(//div[@class='el-popover el-popper new-2-0-ai-ui-header-customer-content']/div[@title='" + status + "']/span)[2]";
        uiDriver.getWebDriver().findElement(By.xpath(xpath_str)).click();
    }

    //通话状态
    public String getICallStatus() {
//        return ui.css(".CallCenter001_wh + span").getText();
        return ui.xpath("//a[@class='current-title el-popover__reference']/span").getText();
    }

    //通话状态 有倒计时
    public String getICallStatusForTime() {
        return ui.css(".CallCenter001_wh +div + span").getText();
    }

    //桌面切换到辅助应答
    public void switchToHelp(String dropDownValue) {
        TimeUtil.sleep(1);
        uiDriver.xpath("//i[@class='status ai-service-icon-img']").click();
        String switchXpath = "//li[@class='el-dropdown-menu__item' and text()='" + dropDownValue + "']";
        TimeUtil.sleep(1);
        uiDriver.xpath(switchXpath).click();
    }

}
