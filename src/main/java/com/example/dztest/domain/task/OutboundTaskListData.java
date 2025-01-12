package com.example.dztest.domain.task;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

@Data
public class OutboundTaskListData {

    @ExcelProperty(value = "用户姓名（选填）", index = 0)
    private String name;

    @ExcelProperty(value = "联系方式", index = 1)
    private String phone;

    public OutboundTaskListData(String name, String phone) {
        this.name = name;
        this.phone = phone;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
