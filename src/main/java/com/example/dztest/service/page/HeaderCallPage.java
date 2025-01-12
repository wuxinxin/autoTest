package com.example.dztest.service.page;

import org.springframework.stereotype.Service;

@Service
public class HeaderCallPage extends BasePage {
    //点击 挂断
    public void clickHangUp(){
        uiDriver.xpath("//div[@class='dzicall-button-container dzicall-button dzicall-hangupButton']").click();
    }
}
