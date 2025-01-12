package com.example.dztest.utils.math;

public class MathInt {

    /*获取最大值*/
    public static int getMax(int[] arr) {
        int max =arr[0];
        for(int x =1;x<arr.length;x++){
            if(arr[x]>max){
                max =arr[x];
            }
        }
        return max;
    }

}
