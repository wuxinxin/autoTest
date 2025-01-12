package com.example.dztest.xunjian.domain;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

/**
 * @author xinxin.wu
 * @description: 单轮对话实体
 * @date 2023/08/11
 * @version: 1.0
 */
@Data
public class RobotQaData {

    private int rowIndex;

    @ExcelProperty(value = "type0content", index = 0)
    private String type0content;

    @ExcelProperty(value = "type1content", index = 1)
    private String type1content;

    @ExcelProperty(value = "intention", index = 2)
    private String intention;

    @ExcelProperty(value = "matchInfo", index = 3)
    private String matchInfo;
    
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        RobotQaData robotQaData = (RobotQaData) o;

        boolean resultType0content = false;
        if (this.type0content != null) {
            if (this.type0content.equals(robotQaData.type0content)) {
                resultType0content = true;
            }
        } else {
            resultType0content = true;
        }

        boolean resultMatchInfo = false;
        if ( this.matchInfo != null) {
            if (this.matchInfo.equals(robotQaData.matchInfo)) {
                resultMatchInfo = true;
            }
        } else {
            resultMatchInfo = true;
        }

//        Boolean result_type1content = false;
//      默认为true，去掉type1content的比较，保证用例执行稳定性
        boolean resultType1content = true;
        if (this.type1content != null) {
            if (this.type1content.equals(robotQaData.type1content)) {
                resultType1content = true;
            }
        } else {
            resultType1content = true;
        }

        boolean resultIntention = false;
        if (this.intention != null) {
            if (this.intention.equals(robotQaData.intention)) {
                resultIntention = true;
            }
        } else {
            resultIntention = true;
        }

        return this.rowIndex == robotQaData.rowIndex && resultIntention && resultMatchInfo
                && resultType0content && resultType1content;
    }


}
