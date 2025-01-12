package com.example.dztest.apicase.function;

import com.example.dztest.utils.math.MathRand;

/**
 * @author xinxin.wu
 * @description: 内置函数，获取随机8位数
 * @date 2023/10/18
 * @version: 1.0
 */
public class MathRandFunction implements Function {
    @Override
    public String execute(String[] args) {
        return MathRand.getRandom8();
    }

    @Override
    public String getReferenceKey() {
        return "random8";
    }

}
