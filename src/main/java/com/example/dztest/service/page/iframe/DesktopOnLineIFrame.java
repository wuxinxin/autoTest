package com.example.dztest.service.page.iframe;

import org.springframework.stereotype.Service;

/**
 * 桌面online iframe
 * 桌面菜单右侧内容页面
 */
@Service
public class DesktopOnLineIFrame extends BaseIFrame{

    //点击 更多功能
    public void clickMore(){
        goToIFrame();
//        ui.findBySpanContainsText("更多功能").click();
        ui.xpath("//p[text()='更多功能']").click();
    }
    //点击 外呼任务
    public void clickCallOutTask(){
        goToIFrame();
//        ui.findBySpanText("外呼任务").click();
        ui.xpath("//p[text()='外呼任务']").click();
    }

    @Override
    protected void onGoToIFrame() {
        ui.switch_to_default_content();
        ui.switchToByXpath("//iframe[@id='online']");
    }
}