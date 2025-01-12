package com.example.dztest.utils.math;

public class MathRand {

    //随机给出一个8位数
    public static String getRandom8(){
        String num = "";
        //定义尾号，尾号是8位
        final int LENPHONE = 8;

        //循环剩下的位数
        for (int i = 0; i < LENPHONE; i++) {
            //每次循环都从0~9挑选一个随机数
            num += (int) (Math.random() * 10);
        }
        return num;
    }
}
