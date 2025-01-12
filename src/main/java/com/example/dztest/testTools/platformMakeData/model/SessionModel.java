package com.example.dztest.testTools.platformMakeData.model;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

/**
 * @description: TODO
 * @author xinxin.wu
 * @date 2024/01/24
 * @version: 1.0
 */

@Data
public class SessionModel {

    @ExcelProperty("key")
    private String key;

    @ExcelProperty("business_id")
    private String businessId;

    @ExcelProperty("content")
    private String content;

    @ExcelProperty("roleType")
    private Integer roleType;

    @ExcelProperty("seqNo")
    private Integer seqNo;

}
