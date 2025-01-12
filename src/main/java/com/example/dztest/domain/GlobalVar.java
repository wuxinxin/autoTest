package com.example.dztest.domain;

import org.openqa.selenium.WebDriver;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class GlobalVar {
    public static final List<Map<String, Object>> BASE_DATA = new ArrayList<>();

    //保存B端登录信息,全局变量
    public static Map<String, Object> GLOBAL_DATA_MAP = new HashMap<>();
    public static List<Map<String, Object>> GLOBAL_DATA_LIST = new ArrayList<>();

    //保存A端登录信息,全局变量
    public static Map<String, Object> A_GLOBAL_DATA_MAP = new HashMap<>();
    public static List<Map<String, Object>> A_GLOBAL_DATA_LIST = new ArrayList<>();

    public static List<String> ROBOT_INFO_EXPECT = new ArrayList<>();
    public static List<String> ROBOT_INFO_ACTUAL = new ArrayList<>();
}
