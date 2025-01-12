package com.example.dztest.utils.db.rollback.db.util;

import lombok.extern.slf4j.Slf4j;

/**
 * 系统工具类
 *
 * @author jian.ma@msxf.com
 * @version 1.0
 * Created by jian.ma@msxf.com on 2022/06/11.
 */
@Slf4j
public abstract class SystemUtil implements BaseSysUtil {

    public static String getSystemProperties(String key) {
        return System.getProperties().getProperty(key);
    }

    public static Integer getSystemProperties2Int(String key) {
        Integer value = null;
        try {
            String property = System.getProperties().getProperty(key);
            if (property != null) {
                value = Integer.valueOf(property);
            }
        } catch (Exception e) {
            log.error("", e);
        }
        return value;
    }

    public static Long getSystemProperties2Long(String key) {
        Long value = null;
        try {
            String property = System.getProperties().getProperty(key);
            if (property != null) {
                value = Long.valueOf(property);
            }
        } catch (Exception e) {
            log.error("", e);
        }
        return value;
    }
}