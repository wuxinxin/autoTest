package com.example.dztest.service.interfaces.kms.robot_entity;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

/**
 *外呼机器人转人工列表实体
 *意图名称，意图表达数，状态
 */
@Getter
@Setter
@EqualsAndHashCode
@Component(value = "newOut2")
public class ToManualRule {
    @ExcelProperty(value = "name", index = 0)
    private String name;

    @ExcelProperty(value = "fullRuleText", index = 1)
    private String fullRuleText;

    @ExcelProperty(value = "actionContent", index = 2)
    private String actionContent;

    @ExcelProperty(value = "actionType", index = 3)
    private String actionType;
}
