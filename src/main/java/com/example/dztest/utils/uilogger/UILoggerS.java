package com.example.dztest.utils.uilogger;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * @ClassName: UILoggerS
 * @description: define annotation
 * @author: jian.ma@msxf.com
 * @create: 2022/03/02
 **/
@Documented
@Retention(RetentionPolicy.RUNTIME)
public @interface UILoggerS {
    UILogger[] value();
}
