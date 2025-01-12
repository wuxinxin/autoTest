package com.example.dztest.utils.db.rollback;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.METHOD;

/**
 * 单库多表数据回滚注解
 * 缺点：在监听期间别人产生数据也会被监听到，所以也会被回滚，所以建议是专用自动化环境时使用
 * @author jian.ma@msxf.com
 * @version 1.0
 * Created by jian.ma@msxf.com on 2022/06/11.
 */
@Retention(java.lang.annotation.RetentionPolicy.RUNTIME)
@Target({METHOD})
public @interface RollBack {

    /**
     * 数据库名称
     * @return
     */
    String dbName() default "";

    /**
     * 数据库表名称
     * @return
     */
    String[] tableName() default {};

    /**
     * 开启或关闭
     * @return
     */
    boolean enabled() default true;
}
