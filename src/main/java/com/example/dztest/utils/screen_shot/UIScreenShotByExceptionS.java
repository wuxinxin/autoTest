package com.example.dztest.utils.screen_shot;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * @ClassName: UIScreenShotByExceptionS
 * @description: define annotation
 * @author: jian.ma@msxf.com
 * @create: 2021/11/15
 **/
@Documented
@Retention(RetentionPolicy.RUNTIME)
public @interface UIScreenShotByExceptionS {
    UIScreenShotByException[] value();
}
