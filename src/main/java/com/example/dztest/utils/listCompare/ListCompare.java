package com.example.dztest.utils.listCompare;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * list比较工具类
 */
public class ListCompare {
    /**
     * 判断集合元素是否相同
     *
     * @param list_a_l 集合a
     * @param list_b_l 集合b
     * @param <Q>      泛型
     * @return
     */
    public static <Q> boolean equals(List<Q> list_a_l, List<Q> list_b_l) {
        ArrayList<Q> list_a = new ArrayList<>(list_a_l);
        ArrayList<Q> list_b = new ArrayList<>(list_b_l);
        //2个都为空,则直接判定相等
        if ((list_a == null || list_a.isEmpty()) && (list_b == null || list_b.isEmpty())) {
            return true;
        }
        //size判断
        if (list_a.size() != list_b.size()) {
            System.out.println("size is not equal，return false!");
            return false;
        }
        Q a;
        Q b;
        for (int i = 0; i < list_a.size(); i++) {
            a = list_a.get(i);
            for (int x = list_b.size() - 1; x >= 0; x--) {//这个是重点，要倒序遍历
                b = list_b.get(x);
                System.out.println("list_a is" + a + "   list_b is" + b);
                if (a.equals(b)) {//remove相等的元素
                    System.out.println("有一个元素的值是相等的");
                    list_b.remove(b);
                    break;
                } else {
                    if (x == 0) {
                        System.out.println("没有一个元素是相等的");
                        return false;
                    }
                }
            }
        }
        return true;
    }

    public static List<String> subList2(List<String> list1, List<String> list2) {
        Map<String, String> tempMap = list2.parallelStream().collect(Collectors.toMap(Function.identity(), Function.identity(), (oldData, newData) -> newData));
        return list1.parallelStream().filter(str -> {
            return !tempMap.containsKey(str);
        }).collect(Collectors.toList());
    }
}
