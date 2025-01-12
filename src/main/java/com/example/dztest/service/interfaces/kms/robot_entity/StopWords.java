package com.example.dztest.service.interfaces.kms.robot_entity;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

/**
 * 停用词信息
 */
@Getter
@Setter
@EqualsAndHashCode
@Component(value = "e3")
public class StopWords {
    @ExcelProperty(value = "standWord", index = 0)
    private String standWord;

    @ExcelProperty(value = "similarWord", index = 1)
    private String similarWord;

    @ExcelProperty(value = "status", index = 2)
    private String status;
}
