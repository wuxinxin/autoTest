package com.example.dztest.utils.excel.base;

import com.example.dztest.common.annotations.MValue;

import java.lang.reflect.Field;

/**
 * 通用注解工具类
 * @author jian.ma@msxf.com
 * @version 1.0 Created by jian.ma on 2022/07/01.
 */

public abstract class AnnotationUtils {
    public static boolean isMValue(Field[] declaredFields) {
        for (Field declaredField : declaredFields) {
            if (declaredField.isAnnotationPresent(MValue.class)) {
                MValue myAnnotation = declaredField.getAnnotation(MValue.class);
                if (null != myAnnotation) {
                    return true;
                }
            }
        }
        return false;
    }
}
