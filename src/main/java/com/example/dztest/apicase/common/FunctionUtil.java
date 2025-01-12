package com.example.dztest.apicase.common;

import com.example.dztest.apicase.function.Function;
import org.reflections.Reflections;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * @author xinxin.wu
 * @description: 内置函数处理
 * @date 2023/10/10
 * @version: 1.0
 */
public class FunctionUtil {

    /**
     * 内置函数集合：key为函数名，value为com.sen.api.functions路径下所有继承/实现/等于Function.class的类的反射对象
     */
    private static final Map<String, Class<? extends Function>> functionsMap = new HashMap<>();

    /**
     * @description: 执行入参函数，并返回函数执行结果
     * @param functionName 函数名
     * @param args         函数入参
     * @return String 函数执行结果
     */
    public static String getValue(String functionName, String[] args) {
        try {
            //通过函数名取到这个函数类的反射对象，构造一个新的实例对象，并且调用实例的excute方法
            return functionsMap.get(functionName).newInstance().execute(args);
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    /**
     * @description: 初始化Function的子类保存到functionsMap
     * @param
     * @return void
     */
    public static void setFunctionsMap() {
        try {
            //初始化Reflections工具类，Reflections是一个反射框架
            Reflections reflections = new Reflections("com.example.dztest.apicase.function");
            //获取casehandle目录下CaseHandler.clss的子类
            Set<Class<? extends Function>> classes = reflections.getSubTypesOf(Function.class);

            for (Class<? extends Function> clazz : classes) {
                Function function = clazz.getDeclaredConstructor().newInstance();
                functionsMap.put(function.getReferenceKey(), clazz);
            }

        } catch (Exception err) {
            err.printStackTrace();
        }
    }

    /**
     * @description: 判断是否是内置函数
     * @param key 函数名
     * @return boolean  true:是；false：否
     */
    public static boolean isFunction(String key) {
        return functionsMap.containsKey(key);
    }

}
