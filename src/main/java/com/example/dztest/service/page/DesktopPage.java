package com.example.dztest.service.page;

import com.example.dztest.service.page.frontonline.FrontOnlinePage;
import com.example.dztest.service.page.iframe.DesktopCallOutIFrame;
import com.example.dztest.service.page.iframe.DesktopOnLineIFrame;
import com.example.dztest.ui.UIDriver;
import com.example.dztest.utils.TimeUtil;
import org.hibernate.validator.constraints.Length;
import org.openqa.selenium.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.swing.table.TableModel;
import java.sql.Time;
import java.util.List;

/**
 * 桌面
 */
@Service
public class DesktopPage extends BasePage {
    @Autowired
    private FrontOnlinePage frontOnlinePage;
    @Autowired
    DesktopCallOutIFrame desktopCallOutIFrame;
    @Autowired
    private DesktopOnLineIFrame desktopOnLineIFrame;

    @Autowired
    private UIDriver ui;

    //导航-桌面
    public void clickNavDesktop() {
        uiDriver.xpath("//div[@class='new-2-0-ai-ui-aside-item'][2]").click();
    }
    //导航-配置管理
    public void clickSetting(){
        uiDriver.xpath("//div[@class='new-2-0-ai-ui-aside-item'][last()]").click();
    }
    //业务记录配置
    public void clickBsConfig(){
        TimeUtil.sleep(3);
        uiDriver.xpath("//div[@class='children-item']/span[contains(text(),'业务记录配置')]").click();
    }
    //在线设置-在线基础设置
    public void clickOnlineConfig(){
        TimeUtil.sleep(1);
        uiDriver.xpath("//div[@class='new-2-0-ai-ui-aside-item'][last()]").click();
        TimeUtil.sleep(1);
        uiDriver.xpath("//span[text()='会话设置']").click();
        TimeUtil.sleep(1);
        uiDriver.xpath("//div[@class='children-item']/span[text()='在线基础配置']").click();
        TimeUtil.sleep(2);
        uiDriver.xpath("//span[text()='客户超时未回复自动结束会话']/../div[@class='el-switch']/span[@class='el-switch__core']").click();
        TimeUtil.sleep(1);
        uiDriver.xpath("//div[@class='save']/button[@class='el-button el-button--primary el-button--small']").click();
    }


    //点击添加一级分类
    public void clickClassification(){
        TimeUtil.sleep(3);
        uiDriver.xpath("//div[@class='detail']/a[@class='ai-font-blue'][1]").click();
    }
    //添加分类保存并置顶
    public void sendClassificationName(String name){
        TimeUtil.sleep(3);
        uiDriver.xpath("//input[@placeholder='请输入分类名称']").sendKeys(name);
        TimeUtil.sleep(3);
        uiDriver.xpath("//div[@class='add2 edit']//i[@class='el-icon-circle-check el-input__icon'][1]").click();
        TimeUtil.sleep(3);
        uiDriver.xpath("//div[@class='ai-category-item-footer']/button[@class='el-button el-button--primary el-button--small']").click();
        TimeUtil.sleep(3);
        uiDriver.xpath("//div[@class='normal' and @title = '" + name + "']//i[@class='el-icon-upload2']").click();
        TimeUtil.sleep(3);
        uiDriver.xpath("//div[@class='ai-category-item-footer']/button[@class='el-button el-button--primary el-button--small']").click();
    }

    //B端桌面点击发送
    public void clickSend() {
        uiDriver.xpath("//button[@class='el-button meterial-down el-button--primary el-button--mini']/span").click();
    }

    /**
     * 发送个人快捷语
     */
    public void sendSimpleMessage(String simpleName) {
        //1.点击快捷语图标
//        uiDriver.xpath("//div[@class='bottom-div boder-bottom-div']/div[@class='imgDiv ai-tooltip personCut1']").click();
        uiDriver.xpath("//i[@class='el-tooltip ai-service-icon-dt-perfast']").click();
        //2.点击快捷语分类：未分类
//        uiDriver.xpath("//div[@class='ai-collapse-item__header']/i[@class='ai-collapse-item__arrow ai-icon-arrow-right']").click();
        uiDriver.xpath("//div[@class='el-collapse-item__header']/i[@class='el-collapse-item__arrow el-icon-arrow-right']").click();
        String simpleNamePath = "//div[@class='teamlist']/..//span[text()='" + simpleName + "']";
        uiDriver.xpath(simpleNamePath).click();
        this.clickSend();

    }


    /**
     * 发送知识库
     */
    public void sendKnowledge(String name) {
        //1.点击知识库图标
        uiDriver.xpath("//i[@class='el-tooltip ai-service-icon-dt-aiknow']").click();
        this.clickDropDownList();
        this.chooseRobotByName(name);
        //2.点击名为name的知识库
        String simpleNamePath = "//div[@class='knowledge-item']//div[@title='" + name + "']/../..//span[text()='引用']";
        uiDriver.xpath(simpleNamePath).click();
        this.clickSend();
    }

    public void inviteApprise() {
        uiDriver.xpath("//div[@class='ai-edit__popup-item']/img[@alt='评价']").click();
    }

    //点击下拉框
    public void clickDropDownList() {
        uiDriver.xpath("//i[@class='el-icon-arrow-down el-icon--right']").click();
    }

    //点击名字为name的li机器人选项
    public void chooseRobotByName(String name) {
        List<WebElement> elements = uiDriver.getWebDriver().findElements(
                By.xpath("//ul[@class='el-dropdown-menu el-popper']/li[@class='el-dropdown-menu__item' ]"));
        for (int i = 0; i < elements.size(); i++) {
            if (name.equals(elements.get(i).getText())) {
                elements.get(i).click();
                break;
            }
        }
    }

    //点击标星客户
    public void clickStars() {
        uiDriver.xpath("//div[@class='ai-chat--room-tel-left']/img[@class='user-icon'][2]").click();
    }

    //点击标星客户样式属性值
    public boolean getStarsAttr() {
        String style = uiDriver.getWebDriver().findElement(
                        By.xpath("//div[@class='ai-chat--room-tel-left']/img[@class='user-icon'][2]"))
                .getAttribute("style");

        return style.equals("display: none;");
    }

    //获取修改标星客户的提示消息
    public String getStarsMsg() {
        return uiDriver.xpath("//div[@class='notification-content']/span").getText();
    }

    //b端撤回指定消息
    public void clickReSend(String msg) {
        String msgXpath = "//div[@class='ql-editor' and text()='" + msg + "']";
        uiDriver.xpath(msgXpath).click();
        uiDriver.xpath("//div[@class='content-item']/span[@class='message-withdraw']").click();
    }

    //获取B、C端最新发送的表情名称,注意切换driver
    public String getNewestExpression() {
        List<WebElement> elements = uiDriver.getWebDriver().findElements(By.xpath("//img[@class='ai-chatRoom__emoji']"));

        return elements.get(elements.size() - 1).getAttribute("alt");
    }

    public boolean isContainsStr(String msg) {
        String text = uiDriver.xpath("//span[@class='revoke-content']").getText();
        return text.contains(msg);
    }

    //b端桌面发送消息
    public void sendTextMsg(String msg) {
        //本地通过在华为云服务器跑时会存在：元素找不到，sendkeys输入值缺失首字问题规避
        WebElement element = uiDriver.xpath("//div[@class='ai-edit__edit is-placeholder edit-chat-wraper edit-chat-box']");
        element.click();
        element.clear();
        element.sendKeys(msg);
        this.clickSend();
    }

    //b端填写客户姓名
    public void inputCustomName(String name) {
        //点击客户信息图标
        uiDriver.xpath("//div[contains(@class, 'imgDiv ai-tooltip customer')]").click();
        //填写名字
        uiDriver.xpath("//input[@placeholder='填写姓名']").sendKeys(name);
    }

    //滚动到元素位置
    public void scrollToElement(WebElement element) {
        JavascriptExecutor js = (JavascriptExecutor) uiDriver.getWebDriver();
        js.executeScript("arguments[0].scrollIntoView();", element);
    }

    //保存客户消息
    public void saveCustomInfo() {
        //先点击客户信息头
        uiDriver.xpath("//div[@class='slotTile']").click();

        //没法滚动？TODO
        //滚动到业务小结附近
        WebElement element = uiDriver.getWebDriver().findElement(By.xpath("//label[@class='ai-form-item__label' and text()='业务小结']"));
        //this.scrollToElement(element);

        //手动点击到业务记录附近
        uiDriver.xpath("//div[contains(@class, 'imgDiv ai-tooltip business')]").click();

        List<WebElement> elements = uiDriver.getWebDriver().findElements(
                By.xpath("//button[@class='ai-button ai-button--primary ai-button--small']/span[text()='保存']"));
        TimeUtil.sleep(1);

        elements.get(0).click();
    }

    //获取保存客户信息的提示消息
    public String getSaveCustomMsg() {
        return uiDriver.xpath("//div[@class='ai-message ai-message--success']/p[@class='ai-message__content']").getText();
    }


    //新建工单
    public void newWorkOrder(String workOrderName) {
        //1.点击工单图标
//        uiDriver.xpath("//div[@class='imgDiv ai-tooltip workOrder1']").click();
        uiDriver.xpath("//i[@class='el-tooltip ai-service-icon-dt-asworkorder']").click();
        TimeUtil.sleep(8);
        //2.点击新建工单
        uiDriver.xpathClick("//button[@class='el-button el-button--text el-button--small']").click();
        TimeUtil.sleep(2);

        //.输入工单标题，需要先进入工单iframe
        desktopCallOutIFrame.switchToWorkOrderFrame();
        uiDriver.xpath("//input[@placeholder='请输入工单标题']").sendKeys(workOrderName);
        TimeUtil.sleep(1);
        //3.默认提交工单
        uiDriver.xpath("//button[@class='el-button el-button--danger el-button--small']/span").click();
        //切换到父frame online
        uiDriver.getWebDriver().switchTo().parentFrame();
        TimeUtil.sleep(1);
    }

    //点击对应工单查看详情
    public void click2WorkOrderDetail(String workOrderName) {
        TimeUtil.sleep(2);
        String xpath = "//div[@class='titleCard elips orderTitle' and @title='" + workOrderName + "']";
        List<WebElement> elements = ui.getWebDriver().findElements(By.xpath(xpath));
        elements.get(0).click();
        TimeUtil.sleep(2);
    }

    public String getWorkOrderNameFromDetail() {
        //先进入工单详情iframe
        desktopCallOutIFrame.switchToWorkOrderDetailFrame();
        TimeUtil.sleep(2);
        WebElement element = uiDriver.xpath("//span[@class='tittleText']");
        return element.getText();
    }

    public void closeWorkOrderDetail() {
        //切换到父frame online
        uiDriver.getWebDriver().switchTo().parentFrame();
//        uiDriver.xpath("//div[@class='ai-dialog__wrapper']/div[@class='ai-dialog createOrderWrap']/..//button[@aria-label='Close']/i").click();
        uiDriver.xpath("//i[@class='ai-service-icon-ba-stow']").click();
        TimeUtil.sleep(2);
    }

    public void clickEndSession() {
        uiDriver.xpath("//button[@class='el-button dzim-chat-room__top__button el-button--default el-button--mini']/span[contains(text(),'结束')]").click();
    }

    //获取结束会话时业务小结必填提示消息
    public String getEndSessionMsg() {
        return uiDriver.xpath("//p[@class='el-message__content']").getText();
    }

    //点击业务小结
    public void clickBsSummary() {
        //点开业务小结
//        uiDriver.xpath("//span[@class='ai-cascader__label']").click();
        TimeUtil.sleep(1);
        uiDriver.xpath("//span[@class='el-input__suffix-inner']/i[@class='el-input__icon el-icon-arrow-down']").click();
        //选择一级分类1
//        uiDriver.xpath("//li[@class='ai-cascader-menu__item']").click();
        TimeUtil.sleep(1);
        uiDriver.xpath("//span[@class='el-cascader-node__label']").click();
    }

    //保存业务小结
    public void saveBsInfo() {
        List<WebElement> elements = uiDriver.getWebDriver().findElements(
                By.xpath("//button[@class='el-button el-button--primary el-button--small']/span[text()='保存']"));
        elements.get(1).click();
    }

    //获取业务小结保存后的提示消息
    public String getSaveMsg() {
        return uiDriver.xpath("//div[@class='ai-notification__content']/p").getText();
    }

    //获取结束会话时的二次确认消息
    public String getDubblebMsg() {
        return uiDriver.xpath("//div[@class='el-message-box__message']/p").getText();
    }

    //二次确认时确定关闭会话
    public void clickSure() {
        uiDriver.xpath("//button[@class='el-button el-button--default el-button--small el-button--primary ']" +
                "/span[contains(text(),'确定')]").click();
    }

    //获取进行中会话状态String
    public String getIngSessionNum() {
        return uiDriver.xpath("//div[@class='dzim-menu__first__list-menu-item']//p[contains(text(),'进行中')]/following-sibling::span/i").getText();
    }

    //在线客服桌面点击会话转接,返回简短附言
    public String clickSessionTransfer(String name) {
        uiDriver.xpath("//button[@class='el-button dzim-chat-room__top__button el-button--default el-button--mini']/span[contains(text(),'转出')]").click();

        String searchPath = "//input[@placeholder='搜索客服姓名，支持模糊搜索']";
        uiDriver.xpath(searchPath).sendKeys(name);
        uiDriver.xpath(searchPath).sendKeys(Keys.ENTER);
        TimeUtil.sleep(1);

        //选中客服列表复选框
        uiDriver.xpath("//span[@class='el-tree-node__expand-icon el-icon-caret-right']").click();
        TimeUtil.sleep(2);
        uiDriver.xpath("//div[@class='el-tree-node is-focusable']//span[@class='el-checkbox__inner']").click();
//        List<WebElement> elements = uiDriver.getWebDriver().findElements(
//                By.xpath("//span[@class='el-checkbox__inner']"));
//        elements.get(2).click();//跳过消息通知的元素，下架消息通知后改为elements.get(1).click();
        TimeUtil.sleep(1);

        //获取简短附言
        String text = uiDriver.xpath("//div[@class='el-textarea']/textarea[@placeholder='简单描述用户的情况给接收客服，100字以内']").getText();
        //点击确定转接
        uiDriver.xpath("//div[@class='footer']/button[@class='el-button el-button--primary el-button--small']/span").click();

        return text;

    }


    //通过是否存在转出附言及转出附言判断是否转出成功
    public boolean isTransferSuccessByMsg(String msg) {
        boolean flag = false;

        List<WebElement> elements = uiDriver.getWebDriver().findElements(By.xpath("//div[@class='ai-chatRoom__bubble']/span"));

        for (int i = 0; i < elements.size(); i++) {
            if (msg.equals(elements.get(i).getText())) {
                flag = true;
                break;
            }
        }

        return flag;
    }

    //进入排队列表并邀请会话
    public void clickToQueueList2Invite() {
        List<WebElement> elements = uiDriver.getWebDriver().findElements(By.xpath("//div[@class='dzim-menu__first__list-menu-item']//p[contains(text(),'会话排队')]"));
        elements.get(0).click();
        TimeUtil.sleep(1);

        //邀请会话
        List<WebElement> elements1 = uiDriver.getWebDriver().findElements(By.xpath("//button[@class='el-button el-button--text']/span[text()='邀请']"));
        elements1.get(1).click();

        //关闭弹窗
//        uiDriver.xpath("//div[@class='ai-dialog queue-list']/..//i[@class='ai-dialog__close ai-icon ai-icon-close']").click();
        uiDriver.xpath("//div[@class='el-dialog__wrapper base-dialog dzim-online-queue']//button[@class='el-dialog__headerbtn']").click();
    }

    //获取排队人数 String化
    public String getQueueNum() {

//        List<WebElement> elements = uiDriver.getWebDriver().findElements(By.xpath("//div[@class='header-pic']/span"));
//
//        return elements.get(1).getText();
        List<WebElement> elements = uiDriver.getWebDriver().findElements(By.xpath("//div[@class='dzim-menu__first__list-menu-item']//p[contains(text(),'会话排队')]/following-sibling::span/i"));
        return elements.get(0).getText();
    }

    //获取b端最新消息
    public String getNewestMsg() {
        List<WebElement> elements = uiDriver.getWebDriver().findElements(By.xpath("//div[@class='ql-editor-wrap ql-editor-text']/div[1]"));

        return elements.get(elements.size() - 1).getText();
    }

    //在线客服桌面点击会话邀请,返回简短附言
    public String clickSessionInvite(String name) {
        uiDriver.xpath("//button[@class='el-button dzim-chat-room__top__button el-button--default el-button--mini']/span[contains(text(),'邀请')]").click();
        String searchPath = "//input[@placeholder='搜索客服姓名，支持模糊搜索']";
        uiDriver.xpath(searchPath).sendKeys(name);
        uiDriver.xpath(searchPath).sendKeys(Keys.ENTER);
        TimeUtil.sleep(1);

        //选中客服列表复选框
        uiDriver.xpath("//span[@class='el-tree-node__expand-icon el-icon-caret-right']").click();
        TimeUtil.sleep(2);
        uiDriver.xpath("//div[@class='el-tree-node is-focusable']//span[@class='el-checkbox__inner']").click();
        TimeUtil.sleep(1);

        //获取简短附言
        String text = uiDriver.xpath("//div[@class='el-textarea']/textarea[@placeholder='简单描述用户的情况给接收客服，100字以内']").getText();
        //点击确定邀请
        uiDriver.xpath("//div[@class='footer']/button[@class='el-button el-button--primary el-button--small']/span").click();

        return text;

    }
    //点击取消邀请
    public void clickCancelInvite() {
        uiDriver.xpath("//button[@class='el-button dzim-chat-room__top__button el-button--default el-button--mini']/span[contains(text(),'取消')]").click();
    }
    //获取取消邀请提示信息
    public String getCancelInviteMsg() {
        String text =  uiDriver.xpath("//div[@class='el-notification__content']/p").getText();
        return text;
    }
    //点击退出三方会话
    public void clickExitSession() {
        uiDriver.xpath("//button[@class='el-button dzim-chat-room__top__button el-button--default el-button--mini']/span[contains(text(),'退出')]").click();
    }
    //点击接管会话
    public void clickTakeoverSession() {
        uiDriver.xpath("//button[@class='el-button dzim-chat-room__top__button el-button--default el-button--mini']/span[contains(text(),'接管')]").click();
    }
    //点击我参与的会话
    public void clickLastParticipatedSession() {
//        String xpath_str = "(//div[@class='online-entry']//div[@class='ex-scroll__body']/div)[1]";
        uiDriver.xpath("//p[contains(text(),'我参与的')]").click();
        TimeUtil.sleep(1);
        String xpath_session = "//div[@class='dzim-session-item__content']";
        uiDriver.xpath(xpath_session).click();
        TimeUtil.sleep(1);
    }
    //导航-工单中心
    public void clickWorkOrderCenter() {
        uiDriver.xpath("//div[@class='new-2-0-ai-ui-aside-item'][5]").click();
    }

}
