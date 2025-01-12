package com.example.dztest.service.page;

import com.example.dztest.ui.UIDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/*
 * 登录页
 * */
@Service
public class LoginPage extends BasePage {

    @Autowired
    private UIDriver ui;

    //输入登录名
    public void inputUserName(String content) {
        ui.findByPlaceholder("请输入邮箱或工号").sendKeys(content);
    }

    //输入密码
    public void inputPassword(String content) {
        ui.findByPlaceholder("请输入密码").sendKeys(content);
    }

    //点击登录
    public void clickLogin() {
        ui.findBySpanText("登录").click();
    }

    public void clickCheckbox() {
        uiDriver.xpath("//span[@class='el-checkbox__inner']").click();
    }
}
