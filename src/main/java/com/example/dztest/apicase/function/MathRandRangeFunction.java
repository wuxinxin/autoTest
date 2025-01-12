package com.example.dztest.apicase.function;
import java.util.Random;

/**
 * @author yang.liu18
 * @description: 内置函数，(x,y)范围内取随机数
 * @date 2023/12/08
 * @version: 1.0
 */
public class MathRandRangeFunction implements Function{

    @Override
    public String execute(String[] args) {
        int x = Integer.parseInt(args[0]);
        int y = Integer.parseInt(args[1]);
        Random rand = new Random();
        int randomNum = rand.nextInt((y - x) + 1)+ x;
        return String.valueOf(randomNum);
    }

    @Override
    public String getReferenceKey() {
        return "RandXy";
    }
}
