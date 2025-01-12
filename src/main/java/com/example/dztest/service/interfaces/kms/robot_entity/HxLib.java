package com.example.dztest.service.interfaces.kms.robot_entity;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

/**
 * 机器人寒暄信息
 */
@Getter
@Setter
@EqualsAndHashCode
@Component(value = "e1")
public class HxLib {
    @ExcelProperty(value = "categoryName", index = 0)
    private String categoryName;

    @ExcelProperty(value = "standardAsk", index = 1)
    private String standardAsk;

    @ExcelProperty(value = "standardAnswer", index = 2)
    private String standardAnswer;

    @ExcelProperty(value = "status", index = 3)
    private String status;

    @ExcelProperty(value = "similarityCount", index = 4)
    private String similarityCount;
}
