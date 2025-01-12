package com.example.dztest.service.page.frontweb;

import com.example.dztest.service.page.BasePage;
import com.example.dztest.utils.TimeUtil;
import org.openqa.selenium.JavascriptExecutor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/*
 * c端在线页面控制
 * */
@Service
public class FrontWebPage extends BasePage {
    private static final Logger logger = LoggerFactory.getLogger(FrontWebPage.class);

    @Value("${frontweb.url}")
    private String frontweb_url;

    /*
     * 打开c端
     * */
    public void open(String token) {
        String channel_url = frontweb_url + "/vChat?token=" + token + "&showChat=true&layout=true";
        logger.info("打开C端地址：" + channel_url);
        uiDriver.open(channel_url);
    }

    /*
     * 打开c端
     * paramKV 如&PHONE=13799999999&name=test
     * */
    public void openParams(String token, String paramKV) {
        String channel_url = frontweb_url + "/vChat?token=" + token + "&showChat=true&layout=true" + paramKV;
        logger.info("打开C端地址：" + channel_url);
        uiDriver.open(channel_url);
    }

    /*
     * 获取转人工时的客服提醒
     * */
    public String getChatNotice() {
        String xpath = "//div[@class='ai-chatRoom__notice']/span";
        return uiDriver.xpath(xpath).getText();
    }

    /*
     * 文本框输入文本
     * */
    public void inputText(String text) {
        uiDriver.xpath("//div[@placeholder='请简短描述您的问题']").sendKeys(text);
    }

    //鼠标聚焦好直接发送文本消息
    public void inputAfterMouseOn(String text) {
        uiDriver.xpath("//div[@class='edit2 is-placeholder']").sendKeys(text);
        TimeUtil.sleep(1);
        uiDriver.xpath("//div[@class='send']").click();
    }

    /*
     * 文本框点击发送按钮
     * */
    public void clickSend() {
        uiDriver.xpath("//div[@class='send']").click();
    }

    /*
     * 发送图片
     * */
    public void sendPicture(String filename) {
        String filePath = uiDriver.getStaticPath() + "/" + filename;
        //执行js，设置style.display='block'
//        String js = "document.querySelector(\"div[class='picture']>input\").style.display='block'";
//        ((JavascriptExecutor)uiDriver.getWebDriver()).executeScript(js);

        uiDriver.xpath("//div[@class='picture']/input").sendKeys(filePath);
    }

    /*
     * 发送文件
     * */
    public void sendFile(String filename) {
        String filePath = uiDriver.getStaticPath() + "/" + filename;
        uiDriver.xpath("//div[@class='file']/input").sendKeys(filePath);
    }

    /*
     * 获取c端最新发送的消息
     * 此消息是文本，返回文本内容
     * */
    public String getLastText_c() {
        String xpath_str = "//div[@class='ai-chatRoom__right'][last()-0]//div[@class='ql-editor']";
//        String xpath_str = "//div[@class='ai-chatRoom__right'][last()-0]//div[@class='ql-editor']";
        return uiDriver.xpath(xpath_str).getText();
    }

    //获取c端左侧最新消息
    public String getLeftNewestMsg() {
        String xpath_str = "//div[@class='ai-chatRoom__left'][last()-0]//div[@class='ql-editor']";
        return uiDriver.xpath(xpath_str).getText();
    }

    //获取左侧推荐答案
    public String getRecommendQ() {
        return uiDriver.xpath("//div[@class='recommend-link']/p").getText();
    }

    public String getOfflineMsg() {
        return uiDriver.xpath("//div[@class='ql-editor']").getText();
    }

    /*
     * 获取c端最新发送的消息
     * 此消息是图片，返回图片类型元素的src属性值
     * */
    public String getLastPictureSrc_c() {
        String xpath_str = "//div[@class='ai-chatRoom__right'][last()-0]//div[@class='ai-chatRoom__image']/img";
        String src = uiDriver.xpath(xpath_str).getAttribute("src");
        return src;
    }

    /*
     * 获取c端最新发送的消息
     * 此消息是文件，返回文件名字
     * */
    public String getLastFileName_c() {
//        String xpath_str = "//div[@class='ai-chatRoom__right'][last()-0]//p[@class='name']";
        String xpath_str = "//div[@class='ai-chatRoom__file'][last()-0]//p[@class='name']";
        return uiDriver.xpath(xpath_str).getText();
    }

    /*
     * 获取b端最新发送的消息
     * 此消息是文本，返回文本内容
     * */
    public String getLastText_b() {
        String xpath_str = "//div[@class='ai-chatRoom__left'][last()-0]//div[@class='ql-editor']";
        return uiDriver.xpath(xpath_str).getText();
    }

    /*
     * 获取b端最新发送的消息
     * 此消息是图片，返回图片类型元素的src属性值
     * */
    public String getLastPictureSrc_b() {
        String xpath_str = "//div[@class='ai-chatRoom__left'][last()-0]//div[@class='ai-chatRoom__image']/img";
        String src = uiDriver.xpath(xpath_str).getAttribute("src");
        return src;
    }

    /*
     * 获取b端最新发送的消息
     * 此消息是文件，返回文件名字
     * */
    public String getLastFileName_b() {
        String xpath_str = "//div[@class='ai-chatRoom__left'][last()-0]//p[@class='name']";
        return uiDriver.xpath(xpath_str).getText();
    }

    /**
     * 获取发送空消息时的提示信息
     *
     * @return
     */
    public String getBlankMessageTips() {
        String xpath_str = "//div[@class='ex-toast is-active']/span";
        return uiDriver.xpath(xpath_str).getText();
    }

    //获取c端客服图标下的名称：机器人名称或者客服名称
    public String getRobotName() {
        return uiDriver.xpath("//p[@class='cont-customer-name']").getText();
    }

    //C端主动提交评价
    public void commitApprise() {
        uiDriver.xpath("//a[@class='swiper-item evalute']").click();
        uiDriver.xpath("//button[@class='common-button is-active']").click();
        TimeUtil.sleep(3);
    }

    //智能推荐提示文本,猜你想问？
    public String getIntention() {
        return uiDriver.xpath("//div[@class='recommend-title']").getText();
    }

    //获取智能推荐列表第一条
    public String getTheFirstIntention() {
        return uiDriver.xpath("//div[@class='classly-body-list']/div[1]").getText();
    }

    //获取横向智能推荐列表第一条
    public String getRawIntentionFirst() {
        return uiDriver.xpath("//a[@class='swiper-item'][1]").getText();
    }

    //询前表单-点击询前表单
    public void clickPreForm() {
        uiDriver.xpath("//div[contains(text(), '点击填写表单')]").click();
    }

    //询前表单-输入姓名
    public void inputNamePreForm(String name) {
        uiDriver.xpath("//input[@placeholder='请输入姓名']").sendKeys(name);
    }

    //询前表单-输入手机号
    public void inputPhonePreForm(String phone) {
        uiDriver.xpath("//input[@placeholder='请输入手机号']").sendKeys(phone);
    }

    //询前表单-提交
    public void clickSubPreForm() {
        uiDriver.xpath("//button[@class='van-button van-button--primary van-button--normal']").click();
    }
}
