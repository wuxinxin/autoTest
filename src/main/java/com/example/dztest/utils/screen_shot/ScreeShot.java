package com.example.dztest.utils.screen_shot;

import com.example.dztest.domain.ScreenShotInfo;
import org.openqa.selenium.WebDriver;

/**
 * @ClassName: ScreeShot
 * @description: Abstract method of screen shot
 * @author: jian.ma@msxf.com
 * @create: 2021/11/9
 **/

@FunctionalInterface
public interface ScreeShot {

    /**
     * Use webDriver to screen shot with screenType specified,and return the result
     * webDriver maybe chrome,firefox,ie and so on
     *
     * @param webDriver
     * @return
     */
    public ScreenShotInfo takeShot(WebDriver webDriver);
}
