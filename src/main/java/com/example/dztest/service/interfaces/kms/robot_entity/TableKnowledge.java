package com.example.dztest.service.interfaces.kms.robot_entity;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

/**
 * 表格知识信息
 */
@Getter
@Setter
@EqualsAndHashCode
@Component(value = "e7")
public class TableKnowledge {
    @ExcelProperty(value = "repositoryName", index = 0)
    private String repositoryName;

    @ExcelProperty(value = "status", index = 1)
    private String status;

    //表格知识里面的属性行数
    @ExcelProperty(value = "endRow", index = 2)
    private String endRow;
}
