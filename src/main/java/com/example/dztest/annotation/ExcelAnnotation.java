package com.example.dztest.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface ExcelAnnotation {
    /**
     * 列索引
     *
     * @return
     */
    int columnIndex() default 0;

    /**
     * 列名
     *
     * @return
     */
    String columnName() default "";
}
