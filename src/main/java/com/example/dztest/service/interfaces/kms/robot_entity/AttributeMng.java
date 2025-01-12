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
@Component(value = "e5")
public class AttributeMng {
    @ExcelProperty(value = "attributeName", index = 0)
    private String attributeName;

    @ExcelProperty(value = "similarity", index = 1)
    private String similarity;

    @ExcelProperty(value = "script", index = 2)
    private String script;
}
