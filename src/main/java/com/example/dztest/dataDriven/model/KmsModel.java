package com.example.dztest.dataDriven.model;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;
import org.testng.Assert;

/**
 * @description: 问题-标签 实体类
 * @author xinxin.wu
 * @date 2024/01/11
 * @version: 1.0
 */
@Data
public class KmsModel {

    /**
     * 问题
     */
    @ExcelProperty(value = "text")
    private String text;

    /**
     * 期望标签
     */
    @ExcelProperty(value = "label")
    private String label;

    /**
     * 实际标签 classifyName
     */
    @ExcelProperty(value = "act_label")
    private String actLabel;

    /**
     * 期望标签与实际标签是否相等
     */
    @ExcelProperty(value = "result")
    private Boolean isEqual = false;

    @ExcelProperty(value = "answerSource")
    private String answerSource;

    @ExcelProperty(value = "knowledgeSource")
    private String knowledgeSource;

    public void assertion() {
        if (this.label.equals(this.actLabel)) {
            this.isEqual = true;
        }
        Assert.assertTrue(this.isEqual);
    }
}
