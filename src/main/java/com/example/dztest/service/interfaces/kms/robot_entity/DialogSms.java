package com.example.dztest.service.interfaces.kms.robot_entity;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

/**
 * 属性管理信息
 */
@Getter
@Setter
@EqualsAndHashCode
@Component(value = "e10")
public class DialogSms {
    @ExcelProperty(value = "sequence", index = 0)
    private String sequence;

    @ExcelProperty(value = "name", index = 1)
    private String name;

    @ExcelProperty(value = "ruleLable", index = 2)
    private String ruleLable;

    @ExcelProperty(value = "standard", index = 3)
    private String standard;

    @ExcelProperty(value = "value", index = 4)
    private String value;

    @ExcelProperty(value = "smsName", index = 5)
    private String smsName;

    @ExcelProperty(value = "smsSend", index = 6)
    private String smsSend;
}
