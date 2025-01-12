package com.example.dztest.apicase.function;

import java.math.BigDecimal;

/**
 * @description: 整数取和
 * @author xinxin.wu
 * @date 2023/11/09
 * @version: 1.0
 */
public class IntSumFunction implements Function{

    @Override
    public String execute(String[] args) {
        String first = args[0];
        String second = args[1];
        int sum = Integer.parseInt(first) + Integer.parseInt(second);
        return String.valueOf(sum);
    }

    @Override
    public String getReferenceKey() {
        return "intSum";
    }
}
