package com.example.dztest.service.interfaces.kms.robot_entity;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

/**
 * 实体管理信息
 */
@Getter
@Setter
@EqualsAndHashCode
@Component(value = "e4")
public class EntityMng {
    @ExcelProperty(value = "name", index = 0)
    private String name;

    @ExcelProperty(value = "typeName", index = 1)
    private String typeName;

    @ExcelProperty(value = "status", index = 2)
    private String status;
}
