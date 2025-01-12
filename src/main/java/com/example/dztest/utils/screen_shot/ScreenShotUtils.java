package com.example.dztest.utils.screen_shot;

import com.example.dztest.domain.ScreenShotInfo;
import com.example.dztest.utils.uilogger.U2IAnno;
import org.openqa.selenium.WebDriver;

/**
 * @ClassName: ScreenShotUtils
 * @description: real screen shot
 * @author: jian.ma@msxf.com
 * @create: 2021/11/9
 **/
public class ScreenShotUtils implements U2IAnno {
    /**
     * @param driver
     * @param screeShot
     * @return
     */
    public static ScreenShotInfo takeScreenshot(WebDriver driver, ScreeShot screeShot) {
        return screeShot.takeShot(driver);
    }
}
