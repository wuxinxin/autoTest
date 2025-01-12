package com.example.dztest.utils.screen_shot;


import java.lang.annotation.*;

/**
 * @ClassName: UIScreenShotByException
 * @description: define annotation
 * @author: jian.ma@msxf.com
 * @create: 2021/11/9
 **/

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Repeatable(UIScreenShotByExceptionS.class)
public @interface UIScreenShotByException {
    /**
     * appoint the exception type
     * @return
     */
    String exception() default "NoSuchElementException";

    /**
     * appoint the flag of recording log
     * @return
     */
    boolean isLogger() default false;
}