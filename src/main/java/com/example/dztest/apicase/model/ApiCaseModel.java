package com.example.dztest.apicase.model;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

/**
 * @author xinxin.wu
 * @description: 单条用例数据对应实体类
 * @date 2023/10/18
 * @version: 1.0
 */
@Data
public class ApiCaseModel {
    /**
     * 从case_file值获取，测试用例文件名需符合{产品}-{模块/功能}-{优先级}.xlsx格式
     */
    @ExcelProperty(value = "产品")
    private String epic;

    /**
     * 从case_file值获取，测试用例文件名需符合{产品}-{模块/功能}-{优先级}.xlsx格式
     */
    @ExcelProperty(value = "模块/功能")
    private String feature;

    @ExcelProperty(value = "exce表中行数")
    private Integer rowIndex;

    /**
     * 取sheets页名称
     */
    @ExcelProperty(value = "用例名称")
    private String caseName;

    @ExcelProperty(value = "步骤")
    private String steps;

    @ExcelProperty(value = "测试套")
    private String caseSuite;

    @ExcelProperty(value = "处理器")
    private String handler;

    @ExcelProperty(value = "执行")
    private String execute;

    @ExcelProperty(value = "模块")
    private String module;

    @ExcelProperty(value = "请求方式")
    private String httpMethod;

    @ExcelProperty(value = "请求路径")
    private String path;

    @ExcelProperty(value = "头信息")
    private String headers;

    @ExcelProperty(value = "接口入参")
    private String params;

    @ExcelProperty(value = "响应断言")
    private String assertion;

    @ExcelProperty(value = "json提取")
    private String jsonObtain;

    @ExcelProperty(value = "需要运行")
    private String isRun;

    @ExcelProperty(value = "添加人")
    private String author;

    @ExcelProperty(value = "优先级")
    private String priority;

    @ExcelProperty(value = "备注")
    private String remark;

    @Override
    public String toString() {
        if (author != null) {
            return caseName + "_" + author;
        }
        return caseName;
    }
}
