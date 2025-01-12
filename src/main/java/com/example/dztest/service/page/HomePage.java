package com.example.dztest.service.page;

import com.example.dztest.utils.screen_shot.UIScreenShotByException;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * @ClassName: HomePage
 * @description: just for test
 * @author: jian.ma@msxf.com
 * @create: 2021/11/9
 **/

@Component
public class HomePage {
    private static final Logger logger = LoggerFactory.getLogger(HomePage.class);

    @UIScreenShotByException(exception = "NoSuchElementException")
    @UIScreenShotByException(isLogger = false)
    @UIScreenShotByException(exception = "NullPointException")
    public void homePage(WebDriver webDriver) {
        logger.info("In homePage,the webDriver is :{}", webDriver);
        webDriver.get("http://www.baidu.com");
        logger.info("Web title is: {}", webDriver.getTitle());

        webDriver.findElement(By.xpath("//span[text()='登录']")).click();
    }
}
