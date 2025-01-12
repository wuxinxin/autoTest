package com.example.dztest.xunjian.domain;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

/**
 * @author xinxin.wu
 * @description: 任务信息实体
 * @date 2023/08/11
 * @version: 1.0
 */
@Data
public class TaskData {

    @ExcelProperty(value = "模块", index = 0)
    private String module;

    @ExcelProperty(value = "机器人id", index = 1)
    private String robotId;

    @ExcelProperty(value = "外呼流程", index = 2)
    private String flowName;

    @ExcelProperty(value = "机器人名字", index = 3)
    private String robotName;

    @ExcelProperty(value = "名单次数", index = 4)
    private Integer num;

    @ExcelProperty(value = "备注", index = 5)
    private String remarks;

}
