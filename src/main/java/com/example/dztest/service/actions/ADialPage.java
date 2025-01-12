package com.example.dztest.service.actions;

import com.example.dztest.service.page.DialPage;
import com.example.dztest.service.page.LoginPage;
import org.openqa.selenium.WebDriver;
import org.springframework.stereotype.Service;


public class ADialPage {
    private WebDriver driver;
    private DialPage dialPage;

    public ADialPage(WebDriver driver){
        this.driver = driver;
        this.dialPage = new DialPage(driver);
    }

    //切换状态为“在线”
    public void setStatusOnline(){
        dialPage.clickIcall();
        dialPage.clickIcallStatus();
        dialPage.clickIcallStatusOnline();
    }
}
