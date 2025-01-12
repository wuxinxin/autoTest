package com.example.dztest.service.interfaces.kms.robot_entity;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

/**
 * 表格知识属性列
 */
@Getter
@Setter
@EqualsAndHashCode
@Component(value = "e6")
public class TableKnowledgeColumnList {
    @ExcelProperty(value = "rowNo", index = 0)
    private String rowNo;

    @ExcelProperty(value = "entity", index = 1)
    private String value;

    @ExcelProperty(value = "cpu", index = 2)
    private String cpu;

    @ExcelProperty(value = "memory", index = 3)
    private String memory;
}
