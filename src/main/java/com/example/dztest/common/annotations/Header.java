package com.example.dztest.common.annotations;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD, ElementType.PARAMETER})
@Repeatable(Headers.class)
public @interface Header {
    String value();
}
