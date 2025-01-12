package com.example.dztest.xunjian.domain;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

/**
 * @author xinxin.wu
 * @description: 期望的会话信息实体
 * @date 2023/08/11
 * @version: 1.0
 */
@Data
public class SessionData {

    @ExcelProperty(value = "电话", index = 0)
    private String phone;

    @ExcelProperty(value = "会话时长", index = 1)
    private Integer time;

    @ExcelProperty(value = "AI分类", index = 2)
    private String aitagname;

}
