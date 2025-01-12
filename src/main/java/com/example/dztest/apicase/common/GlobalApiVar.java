package com.example.dztest.apicase.common;


import java.util.HashMap;
import java.util.Map;

/**
 * @author xinxin.wu
 * @description: 变量池
 * @date 2023/10/20
 * @version: 1.0
 */
public class GlobalApiVar {
    /**
     * 用户变量池；now_suite_name=last_suite_name,沿用userVars；now_suite_name！=last_suite_name，清空userVars
     */
    public static Map<String, Object> userVars = new HashMap<>();

    /**
     * 全局变量池
     */
    public static Map<String, String> globalVars = new HashMap<>();
}
