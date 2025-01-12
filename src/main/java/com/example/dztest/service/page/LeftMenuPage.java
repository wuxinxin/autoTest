package com.example.dztest.service.page;

import com.example.dztest.ui.UIDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 左侧菜单
 */
@Service
public class LeftMenuPage extends BasePage {
    @Autowired
    private UIDriver ui;

    //导航 桌面
    public void clickDesktop(){
        ui.findBySpanText("桌面").click();
    }

    //外呼
    public void clickCallOut(){
        ui.xpath("//span[text()='外呼' and @class='label-name']").click();
    }

    //外呼 人工外呼
    public void clickCallOutManualCallOut(){
        ui.xpath("//div[@title='人工外呼']").click();
    }


}
