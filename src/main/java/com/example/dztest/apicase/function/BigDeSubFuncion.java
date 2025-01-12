package com.example.dztest.apicase.function;

import java.math.BigDecimal;

/**
 * @description: 精确数值相减
 * @author xinxin.wu
 * @date 2023/11/09
 * @version: 1.0
 */
public class BigDeSubFuncion implements Function {

    @Override
    public String execute(String[] args) {
        String first = args[0];
        String second = args[1];
        BigDecimal bigDecimal = new BigDecimal(first);
        BigDecimal sub = bigDecimal.subtract(new BigDecimal(second));
        return sub.toPlainString();
    }

    @Override
    public String getReferenceKey() {
        return "bigDeSub";
    }

}
