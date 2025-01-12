package com.example.dztest.service.interfaces.kms.robot_entity;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

/**
 * 黑名单信息
 */
@Getter
@Setter
@EqualsAndHashCode
@Component(value = "e11")
public class BlacklistRule {
    @ExcelProperty(value = "sequence", index = 0)
    private String sequence;

    @ExcelProperty(value = "name", index = 1)
    private String name;

    @ExcelProperty(value = "ruleLable", index = 2)
    private String ruleLable;

    @ExcelProperty(value = "ruleValue", index = 3)
    private String ruleValue;
}
