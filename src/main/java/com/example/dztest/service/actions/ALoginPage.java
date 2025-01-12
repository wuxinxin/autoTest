package com.example.dztest.service.actions;

import com.example.dztest.service.page.LoginPage;
import com.example.dztest.ui.UIDriver;
import com.example.dztest.utils.TimeUtil;
import com.example.dztest.utils.uilogger.UILogger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/*
* 登录页--登录操作
*/
@Service
public class ALoginPage {
    @Value("${portal.url}")
    private String url;

    @Autowired
    private LoginPage loginPage;

    @Autowired
    private UIDriver ui;

    //登陆
    @UILogger(desc = "登录智能平台")
    public void login(String account, String pwd) {
        ui.open(url);
        loginPage.inputUserName(account);
        loginPage.inputPassword(pwd);
        loginPage.clickLogin();
    }

    @UILogger(desc = "新账号登录智能平台")
    public void newAccountLogin(String account, String pwd) {
        ui.open(url);
        loginPage.inputUserName(account);
        loginPage.inputPassword(pwd);
        loginPage.clickLogin();
        TimeUtil.sleep(1);
        loginPage.clickCheckbox();
        loginPage.clickLogin();
    }
}
