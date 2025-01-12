package com.example.dztest.service.interfaces.kms.robot_entity;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

/**
 *外呼机器人任务意图列表实体
 *意图名称，意图表达数，状态
 */
@Getter
@Setter
@EqualsAndHashCode
@Component(value = "newOut0")
public class TaskIntention {
    @ExcelProperty(value = "intentionName", index = 0)
    private String intentionName;

    @ExcelProperty(value = "expressionCount", index = 1)
    private String expressionCount;

    @ExcelProperty(value = "status", index = 2)
    private String status;
}
