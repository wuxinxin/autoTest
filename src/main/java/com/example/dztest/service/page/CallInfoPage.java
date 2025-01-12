package com.example.dztest.service.page;

import com.example.dztest.ui.UIDriver;
import com.example.dztest.utils.TimeUtil;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 通话信息
 */
@Service
public class CallInfoPage extends BasePage {
    @Autowired
    private UIDriver ui;

    //可见 弹屏页
    public boolean showCallInfo() {
        ui.switch_to_default_content();
        //切换到online iframe
        WebElement oneline_iframe = ui.getWebDriver().findElement(By.xpath("//iframe[@id='online']"));
        ui.getWebDriver().switchTo().frame(oneline_iframe);
        WebElement webElement = ui.getWebDriver().findElement(By.xpath("//iframe[contains(@src, '/speech/front/main/callingIframe')]"));
        boolean styple = webElement.isDisplayed();
        ui.switch_to_default_content();
        return styple;
    }

    //切换到speech iframe
    public void switchSpeechFrame() {
        //切换到online iframe
        ui.switchToByXpath("//iframe[@id='online']");

//        TimeUtil.sleep(1);
//        ui.switchToByXpath("//iframe[contains(@src, '/dzfront/speech/front/main/callingIframe')]");

//        //切换到speech iframe
//        ui.switchToByXpath("//iframe[contains(@src, '/speech/front/main/callingIframe')]");
    }


    //导航 进入 通话信息 页
    public void clickTab() {
        ui.findBySpanContainsText("通话信息").click();
    }

    //选择 通话小结 两级
    public void selectCallSummary2(String first, String second) {
        //点击进行中的数据
        ui.findByLiText(first).click();
        if (second != null) ui.findByLiText(second).click();
    }

    //选择 通话小结 一级
    public void selectCallSummary1(String first) {
//        List<WebElement> elements = ui.getWebDriver().findElements(
//                By.xpath("//span[@class='ai-cascader ai-cascader--small']/span[@class='ai-cascader__label']"));
//        elements.get(0).click();
        ui.xpath("//input[@placeholder='请选择通话小结']").click();
        TimeUtil.sleep(1);
//        ui.xpath("(//span[@class='el-cascader-node__label' and text()='一级分类1']/../label/span/span)[1]").click();
        uiDriver.xpath("//span[@class='el-cascader-node__label' and text()='一级分类1']").click();
        TimeUtil.sleep(1);
        //点击服务备注处，收起下拉栏
//        ui.xpath("//div[@class='el-tabs__item is-top is-active']").click();
//        ui.xpath("//label[@class='el-form-item__label' and text()='服务备注']").click();
//        Actions action = new Actions(ui.getWebDriver());
//        action.moveByOffset(200,200).click().build().perform();
//        ui.getWebDriver().findElement(
//                By.xpath("//li[@class='ai-cascader-menu__item' and text()='一级分类1']")).click();
//
    }
    public void selectCallSummary2(String first){
        ui.xpath("//input[@placeholder='请选择通话小结']").click();
        TimeUtil.sleep(1);
        ui.xpath("(//span[@class='el-cascader-node__label' and text()='一级分类1']/../label/span/span)[1]").click();
        TimeUtil.sleep(1);
//        ui.xpath("//label[@class='el-form-item__label' and text()='服务备注']").click();
        ui.xpath("//i[@class='el-input__icon el-icon-arrow-down is-reverse']").click();
    }

    //输入 服务备注
    public void inputRemarks(String content) {
        List<WebElement> elements = ui.getWebDriver().findElements(By.xpath("//label[text()='业务小结']/../..//textarea"));
        elements.get(0).sendKeys(content);
    }

    //可见 完结 按钮
    public Boolean showEnd() {
        WebElement webElement = ui.getWebDriver().findElement(By.xpath("//label[text()='业务小结']/../../..//span[text()='完结']/.."));
        return webElement.isDisplayed();
    }

    //可见 完结&修改状态为在线  按钮
    public Boolean showEndAndUpdate() {
        WebElement webElement = ui.getWebDriver().findElement(By.xpath("//label[text()='业务小结']/../../..//span[text()='完结']/.."));
        return webElement.isDisplayed();
    }

    //点击 完结
    public void clickEnd() {
        //ui.xpath("//label[text()='业务小结']/../../..//span[text()='完结']").click();

        List<WebElement> elements = ui.getWebDriver().findElements(By.xpath("//label[text()='业务小结']/../../..//span[text()='完结']"));
        elements.get(0).click();
    }

    //点击 完结
    public void clickEndAndUpdate() {
        ui.xpath("//label[text()='业务小结']/../../..//span[text()='完结']").click();
    }

    //点击 暂存
    public void clickCache() {
        ui.xpath("//label[text()='业务小结']/../../..//span[text()='暂存']").click();
    }

    //点击 业务记录
    public void clickWorkNotes() {
        ui.xpath("//span[text()='业务记录']").click();
    }

    //名单资料页
    //点击 名单资料
    public void clickListData() {
        ui.xpath("//span[text()='名单资料']").click();
    }

    //得到客户姓名
    public String getName() {
        return ui.xpath("//label[@for='customerName']/../div/div/input").getAttribute("value");
    }

    //得到客户号码
    public String getNum() {
        return ui.xpath("//label[@for='phoneNumber']/../div/div/input").getAttribute("value");
    }

    //得到备注
    public String getRemarks() {
        return ui.xpath("//label[@for='customerRemark']/../div/div/textarea").getAttribute("value");
    }

    //修改备注
    public void sendRemarks(String remark) {
        WebElement element = ui.xpath("//label[@for='customerRemark']/../div/div/textarea");
        element.clear();
        element.sendKeys(remark);
    }

    //点击保存
    public void clickListSave() {
        ui.xpath("//label[@for='customerRemark']/../..//span[text()='保存']").click();
    }


    //手动外呼任务，外呼判断呼叫弹屏
    public boolean showCallDialog() {
        return ui.getWebDriver().findElement(By.xpath("//span[text()='呼叫弹屏']")).isDisplayed();
    }

    //点击辅助组件
    public void clickComponents(String componentsName) {
        ui.xpath("//span[text()='" + componentsName + "']").click();
    }

    //获取辅助组件src属性值
    public String getComponentsSrc(String componentsName) {
        return ui.xpath("//span[text()='" + componentsName+ "']/../../..//iframe").getAttribute("src");
    }
}
