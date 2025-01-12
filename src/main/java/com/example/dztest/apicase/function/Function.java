package com.example.dztest.apicase.function;

/**
 * @author xinxin.wu
 * @description: 内置函数接口
 * @date 2023/10/09
 * @version: 1.0
 */
public interface Function {

    /**
     * @description: 函数的具体执行
     * @param args 函数的入参
     * @return String   函数执行结果
     */
    String execute(String[] args);

    /**
     * @description: 获取函数名字
     * @return String   函数的名字
     */
    String getReferenceKey();
}

