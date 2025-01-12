package com.example.dztest.service.interfaces.kms.robot_entity;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

/**
 * 对话标签-标签配置校验
 * <p>
 * 标签规则不校验！！！
 */
@Getter
@Setter
@EqualsAndHashCode
@Component(value = "e9")
public class DialogTag {
    @ExcelProperty(value = "name", index = 0)
    private String name;

    @ExcelProperty(value = "refCount", index = 1)
    private String refCount;
}
