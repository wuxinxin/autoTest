package com.example.dztest.service.interfaces.kms.robot_entity;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

/**
 * 问答知识分类
 */
@Getter
@Setter
@EqualsAndHashCode
@Component(value = "e0")
public class QwKnowledge {
    @ExcelProperty(value = "classificationName", index = 0)
    private String classificationName;

    @ExcelProperty(value = "standardAsk", index = 1)
    private String standardAsk;

    @ExcelProperty(value = "standardAnswer", index = 2)
    private String standardAnswer;

    @ExcelProperty(value = "categoryName", index = 3)
    private String categoryName;

    @ExcelProperty(value = "similarityCount", index = 4)
    private String similarityCount;

    @ExcelProperty(value = "status", index = 5)
    private String status;
}
