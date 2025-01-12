package com.example.dztest.service.page.frontonline;

import com.example.dztest.service.page.BasePage;
import com.example.dztest.service.page.DesktopPage;
import com.example.dztest.service.page.HomePage;
import com.example.dztest.utils.TimeUtil;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/*
 * B端在线客服页面控制
 * */
@Service
public class FrontOnlinePage extends BasePage {
    private static final Logger logger = LoggerFactory.getLogger(FrontOnlinePage.class);
    private String xpath_last_c = "//div[@class='ai-chatRoom__right'][last()-0]";
    private String xpath_last_b = "//div[@class='ai-chatRoom__left'][last()-0]";

    @Autowired
    DesktopPage desktopPage;

    /*
     * 切换到online iframe
     * */
    public void switchOnlineIframe() {
        uiDriver.switchToByXpath("//iframe[@id='online']");
    }

    /*
     * 点击在线，展开在线会话
     * */
    public void clickOnline() {
        String xpath_str = "//div[@class='online-title']/span";
        uiDriver.xpath(xpath_str).click();
    }

    /*
     * 点击进行中最近的会话
     * */
    public void clickLastSession() {
//        String xpath_str = "(//div[@class='online-entry']//div[@class='ex-scroll__body']/div)[1]";
        TimeUtil.sleep(2);
        String xpath_str = "//p[contains(text(),'进行中')]";
        uiDriver.xpathClick(xpath_str).click();
        TimeUtil.sleep(1);
        String xpath_session = "//div[@class='dzim-session-item__content']";
        uiDriver.xpathClick(xpath_session).click();
        TimeUtil.sleep(1);
    }

    //获取最新的c端发送的uuid元素
    public WebElement getLastUuid_c() {
        String xpath_str = "//div[@class='ai-chat--core-item' and not(@style='display: none;')]//div[@class='ai-chatRoom']//div[@class='ai-chatRoom__left'][last()-0]";
        return uiDriver.xpath(xpath_str);
    }

    //获取最新的b端发送的uuid元素
    public WebElement getLastUuid_b() {
        String xpath_str = "//div[@class='ai-chat--core-item' and not(@style='display: none;')]//div[@class='ai-chatRoom']//div[@class='ai-chatRoom__right'][last()-0]";
        return uiDriver.xpath(xpath_str);
    }


    //获取文本类型uuid元素的文本内容
    public String getText(WebElement webElement) {
        String text = webElement.findElement(By.xpath("./div/div")).getText();
        return text;
    }

    //获取图片类型uuid元素的src属性值
    public String getPictureSrc(WebElement webElement) {
        String src = webElement.findElement(By.xpath("./img")).getAttribute("src");
        return src;
    }

    //获取文件类型uuid元素的文件名称
    public String getFileName(WebElement webElement) {
        String text = webElement.findElement(By.xpath("./div/div[@class='content-left']/p[@class='name']")).getText();
        return text;
    }

    /*
     * 获取c端最新发送的消息
     * 此消息是文本，返回文本内容
     * */
    public String getLastText_c() {
        String xpath_str = xpath_last_c +  "//div[@class='ql-editor'][last()-0]";
        return uiDriver.xpath(xpath_str).getText();
    }

    //获取辅助模式为仅提示后的b端消息
    public String getTipMsg() {
        return uiDriver.xpath("//div[@class='el-tooltip font-12 assist-chat-message item']/p").getText();
    }

    //提示消息发送
    public void sendTipMsg() {
        uiDriver.xpath("//div[@class='el-tooltip assist-icon send-icon item']/img").click();
    }


    public String getNewestText() {
        List<WebElement> elements = uiDriver.getWebDriver().
                findElements(By.xpath("//div[@class='ql-editor-wrap']/div[@class='ql-editor']"));

        String newestTextMsg = elements.get(elements.size() - 1).getText();

        return newestTextMsg;
    }

    /*
     * 获取c端最新发送的消息
     * 此消息是图片，返回图片类型元素的src属性值
     * */
    public String getLastPictureSrc_c() {
        String xpath_str = xpath_last_c + "//div[@class='ai-chatRoom__image']/img";
        String src = uiDriver.xpath(xpath_str).getAttribute("src");
        return src;
    }

    /*
     * 获取c端最新发送的消息
     * 此消息是文件，返回文件名字
     * */
    public String getLastFileName_c() {
        String xpath_str = xpath_last_c + "//div[@class='ai-chatRoom__file']//p[@class='name']";
        return uiDriver.xpath(xpath_str).getText();
    }

    public String getLastFileName_doc() {
        String xpath_str = "//div[@class='ai-chatRoom__file']";
        return uiDriver.xpath(xpath_str).getAttribute("title");
    }

    /*
     * 获取b端最新发送的消息
     * 此消息是文本，返回文本内容
     * */
    public String getLastText_b() {
        String xpath_str = xpath_last_b + "//div[@class='text-chat-container text-chat-container-box']/div/div";
        return uiDriver.xpath(xpath_str).getText();
    }

    /*
     * 获取b端最新发送的消息
     * 此消息是图片，返回图片类型元素的src属性值
     * */
    public String getLastPictureSrc_b() {
        String xpath_str = xpath_last_b + "//div[@class='ai-chatRoom__image']/img";
        String src = uiDriver.xpath(xpath_str).getAttribute("src");
        return src;
    }

    /*
     * 获取b端最新发送的消息
     * 此消息是文件，返回文件名字
     * */
    public String getLastFileName_b() {
        String xpath_str = xpath_last_b + "//div[@class='ai-chatRoom__file']//p[@class='name']";
        return uiDriver.xpath(xpath_str).getText();
    }

    //关闭当前会话
    public void closeSession() {
        String xpath_str = "//div[@class='ai-chat--room-tel']//span[text()='结束会话']";
        uiDriver.xpath(xpath_str).click();
    }

    //回复框输入文本
    public void inPutText(String text) {
        String xpath_str = "//div[@class='ai-chat--room-edit']//div[@class='ai-edit__edit edit-chat-wraper edit-chat-box']";
        uiDriver.xpath(xpath_str).sendKeys(text);
    }

    //回复框点击发送
    public void clickSend() {
        String xpath_str = "//div[@class='ai-edit__body-bottom']/button";
        uiDriver.xpath(xpath_str).click();
    }

    //发送图片
    public void sendPicture(String filename) {
        String filePath = uiDriver.getStaticPath() + "/" + filename;
        uiDriver.xpath("//img[@title='图片/视频']/../input").sendKeys(filePath);
    }

    //发送文件
    public void sendFile(String filename) {
        String filePath = uiDriver.getStaticPath() + "/" + filename;

        //执行js，设置style.display='block'
//        String js = "document.evaluate(\"//img[@title='文件']/../input\", document).iterateNext().style.display='block'";
//        ((JavascriptExecutor)uiDriver.getWebDriver()).executeScript(js);

        uiDriver.xpath("//img[@title='文件']/../input").sendKeys(filePath);
    }

    public String getInviteMsg() {

        WebElement element = uiDriver.getWebDriver().findElement(By.xpath("//div[@class='evaluate-msg']"));
        String innerText = element.getAttribute("innerText");

        return innerText;
    }

    //客户提交评价
    public void commitApprise() {
        logger.info("客户提交评价");
        uiDriver.xpath("//div[@class='evaluate-content submit-wrap']/button[@class='common-button is-active']").click();
    }

    public boolean isContainsStr(String exp) {
        boolean flag = false;
        List<WebElement> elements = uiDriver.getWebDriver().findElements(
                By.xpath("//div[@class='ai-chatRoom__notice']/span"));

        for (int i = 0; i < elements.size(); i++) {
            String text = elements.get(i).getText();
            if (text.contains(exp)) {
                flag = true;
                break;
            }
        }
        return flag;
    }

    //客户侧是否存在消息撤回的报文消息
    public boolean isReSendMsgExist(String msg) {
        String text = uiDriver.xpath("//div[@class='ai-chatRoom__bubble']/span").getText();
        return text.contains(msg);
    }

    //B端发送表情
    public void sendZy() {
        uiDriver.xpath("//div[@class='ai-edit__popup-item el-popover__reference']/img[@alt='表情']").click();
        uiDriver.xpath("//div[@data-char='呲牙']/img[@alt='呲牙']").click();
        TimeUtil.sleep(5);
        //发送
        desktopPage.clickSend();
//        uiDriver.xpath("//button[@class='ai-button meterial-down ai-button--primary ai-button--mini']/span").click();
    }

    //离线留言最新一条发起会话
    public void startOfflineSession() {
        //关闭通知消息弹窗
//        uiDriver.xpath("//span[text()='我知道了']").click();

        //点击离线留言
//        uiDriver.xpath("//div[@class='leaveMessage']/a/..//span").click();
        TimeUtil.sleep(2);
        uiDriver.xpath("//div[@class='dzim-menu__first__list-menu-item']//p[contains(text(),'离线留言')]").click();
        TimeUtil.sleep(1);

        //选中最新离线会话
//        List<WebElement> elements = uiDriver.getWebDriver().findElements(By.xpath("//span[@class='el-checkbox__inner']"));
//        elements.get(1).click();//多减去2个是通知消息的元素
        uiDriver.xpath("(//span[@class='el-checkbox__inner'])[last()]").click();
        TimeUtil.sleep(1);


        //发起会话
        uiDriver.xpath("//span[text()='发起会话']").click();
        TimeUtil.sleep(1);

        //离线：状态切换确认
        uiDriver.xpath("//button[@class='el-button el-button--default el-button--small el-button--primary ']/span").click();
    }

    //询前表单-根据key获取表单value
    public String getValuePreForm(String key) {
        return uiDriver.xpath("//div[text()='" + key + "']/../div[@class='cont-item-r']").getText();
    }

    //询前表单-切换到询前表单iframe
    public void switchPreFormIframe() {
        uiDriver.switchToByXpath("//iframe");
    }

    //左侧客户信息页,获取字段value
    public String getText(String key) {
        return uiDriver.xpath("//label[@class='el-form-item__label' and text()='" + key + "']/..//input").getAttribute("value");
    }
    //离线留言最新一条生成工单
    public void startOfflineMsg() {
        //点击离线留言
        TimeUtil.sleep(2);
        uiDriver.xpath("//div[@class='dzim-menu__first__list-menu-item']//p[contains(text(),'离线留言')]").click();
        TimeUtil.sleep(2);
        uiDriver.xpath("//td[@class='el-table_1_column_5   el-table__cell']//div[text()='待处理']").click();
        TimeUtil.sleep(2);
        //生成工单
        uiDriver.xpath("//span[text()='生成工单']").click();
        TimeUtil.sleep(5);
        uiDriver.switch_to_window_handles();
        //提交工单
        uiDriver.xpath("//button[@class='el-button el-button--danger el-button--small']").click();
        TimeUtil.sleep(1);
        uiDriver.switch_to_window_handles();
    }
    //获取邀请会话弹窗标题
    public String getPopupTitle() {
        String xpath_str ="//div[@class='dialog-header']/h3";
        return uiDriver.xpath(xpath_str).getText();
    }
    //点击拒绝邀请
    public void clickRefuseInvite() {
        uiDriver.xpath("//button[@class='el-button el-button--default el-button--small']/span[contains(text(),'拒绝')]").click();
    }
    //点击同意邀请并进入会话
    public void clickAgreeInvite() {
        uiDriver.xpath("//button[@class='el-button el-button--primary el-button--small']/span[contains(text(),'同意并进入会话')]").click();
    }

    public void newinputText(String text) {
        uiDriver.xpath("//input[@placeholder='搜索编号、标题、客户、手机号或处理人']").sendKeys(text);
    }
    //获取工单列表数据
    public String getWorderOrderList() {
        return uiDriver.xpath("//div[@class='oneLineText']").getText();
    }
}
