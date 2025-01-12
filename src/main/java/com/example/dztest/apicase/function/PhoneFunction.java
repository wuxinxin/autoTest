package com.example.dztest.apicase.function;

import com.example.dztest.utils.phone.PhoneNumTools;

/**
 * @author xinxin.wu
 * @description: 内置函数，获取随机电话号码
 * @date 2023/10/18
 * @version: 1.0
 */
public class PhoneFunction implements Function {

    @Override
    public String execute(String[] args) {
        return PhoneNumTools.getPhoneNum();
    }

    @Override
    public String getReferenceKey() {
        return "phone";
    }

}
