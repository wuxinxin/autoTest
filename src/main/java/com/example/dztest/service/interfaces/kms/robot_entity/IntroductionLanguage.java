package com.example.dztest.service.interfaces.kms.robot_entity;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

/**
 *外呼机器人引导语列表实体
 *意引导语类型，引导语内容，机器人类型
 */
@Getter
@Setter
@EqualsAndHashCode
@Component(value = "newOut1")
public class IntroductionLanguage {
    @ExcelProperty(value = "guideType", index = 0)
    private String guideType;

    @ExcelProperty(value = "guideContent", index = 1)
    private String guideContent;

    @ExcelProperty(value = "robotType", index = 2)
    private String robotType;
}
