package com.example.dztest.service.page;

import com.example.dztest.ui.UIDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 人工外呼
 */
@Service
public class CallOutManualCallOutPage extends BasePage {
    @Autowired
    private UIDriver ui;

    //输入任务id
    public void inputTaskId(String tid){
        goToSettingSpeechButBoundTaskIframe();
        ui.findByPlaceholder("搜索任务名或ID").sendKeys(tid);
    }

    //点击 任务名称 进入详情页
    public void clickTaskName(String name){
        goToSettingSpeechButBoundTaskIframe();
        ui.findBySpanText(name).click();
    }

    /**
     新建任务
     **/
    public void clickNewTask(){
        goToSettingSpeechButBoundTaskIframe();
//        ui.testPageLoad("//span[contains(text(),'新建任务')]").click();
        ui.execJsClick(ui.xpathUI("//span[contains(text(),'新建任务')]"));
    }

    //输入任务名称
    public void inputTaskName(String content){
        goToSettingSpeechButBoundTaskIframe();
        ui.findByPlaceholder("请输入任务名称，不超过50个字").sendKeys(content);
    }

    //点击自动外呼
    public void inputAutoCall(){
        goToSettingSpeechButBoundTaskIframe();
        ui.xpath("//span[@class='ai-radio-button__inner' and text()='自动外呼 ']").click();
    }

    //点击手动外呼
    public void clickManualCall(){
        goToSettingSpeechButBoundTaskIframe();
        ui.xpath("//span[text()='手动外呼 ']").click();
    }

    //设置时间
    public void inputDay(String start, String end){
        goToSettingSpeechButBoundTaskIframe();
        ui.findByPlaceholder("开始日期").sendKeys(start);
        ui.findByPlaceholder("结束日期").sendKeys(end);
        clickManualCall(); //点击一下 消除日期选则框
    }

    //输入任务描述
    public void inputTaskDesc(String text){
        goToSettingSpeechButBoundTaskIframe();
        ui.xpath("//label[text()='任务描述']/..//textarea").sendKeys(text);
    }

    //点击下一步
    public void clickNext(){
        goToSettingSpeechButBoundTaskIframe();
        ui.findBySpanContainsText("下一步").click();
    }

    //输入上传文件
    public void inputFile(String filePath){
        goToSettingSpeechButBoundTaskIframe();
        ui.xpath("//input[@name='file']").sendKeys(filePath);
        ui.sleep(1000);
    }

    //选择技能组
    public void selectTechGroup(String name){
        goToSettingSpeechButBoundTaskIframe();
        ui.findByPlaceholder("请选择呼叫技能组").click();
        ui.execJsClick(ui.findBySpanText(name));
    }

    //选择技能组
    public void inputCustomerNum(String num){
        goToSettingSpeechButBoundTaskIframe();
        ui.xpath("//div[text()='坐席姓名']/../../../../../..//input").sendKeys(num);
    }

    //点击 提交
    public void clickSubmit(){
        goToSettingSpeechButBoundTaskIframe();
        ui.findBySpanContainsText("提 交").click();
    }

    private void goToSettingSpeechButBoundTaskIframe(){
        ui.switch_to_default_content();
        ui.switchToByXpath("//iframe[@id='setting.speech.outboundTask']");
    }

}
