package com.example.dztest.service.interfaces.kms.robot_entity;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

/**
 * 任务流程列表信息校验entity
 *
 * 任务流程：以下内容需要保持一致：
 * 任务名称
 * 状态
 * 流程节点内容
 * 任务流程数量
 */
@Getter
@Setter
@EqualsAndHashCode
@Component(value = "e8")
public class TaskProcess {
    @ExcelProperty(value = "flowName", index = 0)
    private String flowName;

    @ExcelProperty(value = "active", index = 1)
    private String active;

    @ExcelProperty(value = "sortNumber", index = 2)
    private String sortNumber;
}
