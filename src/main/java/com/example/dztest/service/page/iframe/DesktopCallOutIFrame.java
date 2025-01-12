package com.example.dztest.service.page.iframe;

import com.example.dztest.utils.TimeUtil;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 外呼任务iframe
 * 更多-（手动）外呼任务
 */
@Service
public class DesktopCallOutIFrame extends BaseIFrame {

    //输入任务名称 查询
    public void inputTaskName(String name) {
        goToIFrame();
        ui.findByPlaceholder("搜索任务名称").sendKeys(name);
    }

    public void clickSearch() {
        goToIFrame();
        ui.findBySpanText("查询").click();
//        ui.sleep(2000);
    }

    //点击任务进入任务详情
    public void clickTaskNameN(String name) {
        goToIFrame();
        String xpath = "//td[@class='el-table_1_column_1   el-table__cell']//span[text()='" + name + "']";
        ui.getWebDriver().findElement(By.xpath(xpath)).click();
    }

    //根据用户名拨打电话
    public void call(String name) {
        String xpath = "//div[@class='ai-table__body-wrapper is-scrolling-none']//div[text()='" + name + "' and @class='cell']/../..//i[@class='fw']";
        ui.getWebDriver().findElement(By.xpath(xpath)).click();
    }

    public void clickTaskName(String name) {
        goToIFrame();
        ui.findBySpanText(name).click();
    }

    public void clickCallPhonePng() {
//        ui.css(".fw").click(); //直接点击有错误，只能执行js

        goToIFrame();
//        ui.xpath("(//button[@class='el-button el-button--text']/span)[1]").click();
//        List<WebElement> elements = ui.getWebDriver().findElements(By.xpath("(//button[@class='el-button el-button--text']/span)[1]"));
//        WebElement webElement = elements.get(0);
        WebElement webElement = ui.xpath("(//button[@class='el-button el-button--text']/span)[1]");

        ui.execJs("arguments[0].click();", webElement);
    }

    public void clickCallPhoneOnConfirm() {
        goToIFrame();
//        ui.findBySpanContainsText("拨打").click();
        String xpath = "//button[@type='button']//span[contains(text(),'拨打')]";
        ui.getWebDriver().findElement(By.xpath(xpath)).click();
    }

    public void checkPopUpScreen() {
        goToIFrame();
        ui.findBySpanText("呼叫弹屏");
    }

    public void checkCallOk() {
        goToIFrame();
        ui.findBySpanContainsText("已接通");
    }

    @Override
    protected void onGoToIFrame() {
        ui.switch_to_default_content();
        ui.switchToByXpath("//iframe[@id='online']");
        ui.switchToByXpath("//iframe[contains(@src, '/speech/front/main/callTaskIframe')]");
    }

    public void switchCallTaskFrame() {
        ui.switchToByXpath("//iframe[@id='online']");
        ui.switchToByXpath("//iframe[contains(@src, '/speech/front/main/callTaskIframe')]");
    }

    //桌面工单创建iframe
    public void switchToWorkOrderFrame() {
        ui.switchToByXpath("//iframe[contains(@src, '/workorder/front/main/orderCreate')]");
    }


    //桌面工单详情iframe
    public void switchToWorkOrderDetailFrame() {
        ui.switchToByXpath("//iframe[contains(@src, '/workorder/front/main/orderDetail/work')]");
    }
}