package com.example.dztest.common.annotations;

import java.lang.annotation.*;

/**
 * @ClassName: MValue
 * @description: annotation for getting customize data from excel
 * will support csv? //TODO
 * @author: jian.ma@msxf.com
 * @create: 2022/06/25
 * @update: 2021/06/25
 **/

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface MValue {

    /**
     * The actual value expression &mdash; for example, <code>#{systemProperties.myProp}</code>.
     */
    String value();

}