package com.example.dztest.extentreport.model;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

/**
 * @description: 测试用例执行结果-实体类
 * @author xinxin.wu
 * @date 2024/02/01
 * @version: 1.0
 */
@Data
public class CaseResultModel {
    /**
     * 产品，如外呼机器人
     */
    @ExcelProperty(value = "产品")
    private String product;

    /**
     * 版本，如v2.6.4
     */
    @ExcelProperty(value = "版本")
    private String version;

    /**
     * 模块，如呼叫中心、在线客服；对应xml文件中suite标签的名称
     */
    @ExcelProperty(value = "模块")
    private String module;

    /**
     * 测试套，如呼叫中心技能组管理；对应xml文件中test标签的名称
     */
    @ExcelProperty(value = "测试套")
    private String suite;

    /**
     * 测试用例，如新增呼叫中心技能组；对应xml文件中methods标签下方法的描述
     */
    @ExcelProperty(value = "测试用例")
    private String caseName;

    /**
     * 执行状态，pass-通过，failed-失败，skip-跳过
     */
    @ExcelProperty(value = "结果")
    private String status;

    /**
     * 添加人，用例添加人；v1默认为空，v2读取excel中“添加人”列
     */
    @ExcelProperty(value = "作者")
    private String author;

}
