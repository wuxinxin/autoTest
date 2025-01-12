package com.example.dztest.utils.common;

import com.example.dztest.domain.HttpResponse;
import com.example.dztest.utils.AssertUtil;
import org.apache.poi.ss.formula.functions.T;

import java.util.List;

/**
 * 延时包装-泛型接口
 */
public interface DelayCall<T> {


    /**
     * @param func   函数对象
     * @param expect 期望值
     * @param delay  延迟调用时间
     * @param o      func对应参数
     * @return
     */
    public abstract HttpResponse executeUntil(T func, Object expect, Integer delay, Object... o);
}
