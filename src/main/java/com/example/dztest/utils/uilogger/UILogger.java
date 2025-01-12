package com.example.dztest.utils.uilogger;


import java.lang.annotation.*;

/**
 * @ClassName: UILogger
 * @description: define annotation
 * @author: jian.ma@msxf.com
 * @create: 2022/03/02
 **/

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
//@Repeatable(UILoggerS.class)
public @interface UILogger {
    String module() default "";

    String desc() default "";

    boolean isPage() default false;
}