package com.example.dztest.apicase.function;

import java.math.BigDecimal;

/**
 * @description: 精确数值相加
 * @author xinxin.wu
 * @date 2023/11/03
 * @version: 1.0
 */
public class BigDeSumFunction implements Function{

    @Override
    public String execute(String[] args) {
        String first = args[0];
        String second = args[1];
        BigDecimal bigDecimal = new BigDecimal(first);
        BigDecimal sum = bigDecimal.add(new BigDecimal(second));
        return sum.toPlainString();
    }

    @Override
    public String getReferenceKey() {
        return "bigDeSum";
    }

}
