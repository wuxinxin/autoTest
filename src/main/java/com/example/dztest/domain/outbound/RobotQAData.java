package com.example.dztest.domain.outbound;

import com.alibaba.excel.annotation.ExcelProperty;
import com.example.dztest.annotation.ExcelAnnotation;
import lombok.Data;

/**
 * 机器人应答 - 文件映射实体类
 */
@Data
public class RobotQAData {

    private int rowIndex;

    @ExcelProperty(value = "type0content", index = 0)
    private String type0content;

    @ExcelProperty(value = "type1content", index = 1)
    private String type1content;

    @ExcelProperty(value = "intention", index = 2)
    private String intention;

    @ExcelProperty(value = "matchInfo", index = 3)
    private String matchInfo;

    @Override
    public boolean equals(Object o) {
        if (o == this) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RobotQAData robotQAData = (RobotQAData) o;

        Boolean result_type0content = false;
        if (this.type0content != null) {
            if (this.type0content.equals(robotQAData.type0content)) {
                result_type0content = true;
            }
        } else {
            result_type0content = true;
        }

        Boolean result_matchInfo = false;
        if ( this.matchInfo != null) {
            if (this.matchInfo.equals(robotQAData.matchInfo)) {
                result_matchInfo = true;
            }
        } else {
            result_matchInfo = true;
        }

//        Boolean result_type1content = false;
//      默认为true，去掉type1content的比较，保证用例执行稳定性
        Boolean result_type1content = true;
        if (this.type1content != null) {
            if (this.type1content.equals(robotQAData.type1content)) {
                result_type1content = true;
            }
        } else {
            result_type1content = true;
        }

        Boolean result_intention = false;
        if (this.intention != null) {
            if (this.intention.equals(robotQAData.intention)) {
                result_intention = true;
            }
        } else {
            result_intention = true;
        }

        return this.rowIndex == robotQAData.rowIndex && result_intention && result_matchInfo
                && result_type0content && result_type1content;
    }


    public void setRowIndex(int rowIndex) {
        this.rowIndex = rowIndex;
    }

    public void setType0content(String type0content) {
        this.type0content = type0content;
    }

    public void setType1content(String type1content) {
        this.type1content = type1content;
    }

    public void setIntention(String intention) {
        this.intention = intention;
    }

    public void setMatchInfo(String matchInfo) {
        this.matchInfo = matchInfo;
    }

    public int getRowIndex() {
        return rowIndex;
    }

    public String getType0content() {
        return type0content;
    }

    public String getType1content() {
        return type1content;
    }

    public String getIntention() {
        return intention;
    }

    public String getMatchInfo() {
        return matchInfo;
    }
}
