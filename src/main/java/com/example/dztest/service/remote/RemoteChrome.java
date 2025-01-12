package com.example.dztest.service.remote;

import com.example.dztest.utils.ChromeDriverUtil;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class RemoteChrome {
    @Value("${ui.chrome_remote}")
    private String  chrome_url;

    public WebDriver getRemoteChromeDriver(){
        WebDriver webDriver = ChromeDriverUtil.initRemoteChromeDriver(chrome_url);
        return webDriver;
    }

    public WebDriver getLocalChromeDriver(){
        System.setProperty("webdriver.chrome.driver", "C:\\Program Files (x86)\\Google\\Chrome\\Application\\chromedriver2.exe");
        WebDriver webDriver = new ChromeDriver();
        return webDriver;
    }

}
