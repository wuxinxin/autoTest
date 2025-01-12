package com.example.dztest.domain.outbound;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import com.example.dztest.utils.listCompare.ListCompare;
import lombok.Data;
import org.apache.commons.compress.utils.Lists;

import java.util.ArrayList;
import java.util.List;

/**
 * 机器人应答通话 - 文件映射实体类
 */
@Data
public class RobotSessionData {

    @ExcelIgnore
    private int rowIndex;

    @ExcelIgnore
    private List<String> exp_answerList = Lists.newArrayList();

    @ExcelIgnore
    private List<MatchData> exp_matchDataList = Lists.newArrayList();

    @ExcelIgnore
    private List<String> act_answerList = Lists.newArrayList();

    @ExcelIgnore
    private List<MatchData> act_matchDataList = Lists.newArrayList();

    @ExcelProperty(value = "结果", index = 0)
    private String result;

    @ExcelProperty(value = "question", index = 1)
    private String question;

    @ExcelProperty(value = "exp_answer", index = 2)
    private String exp_answer;

    @ExcelProperty(value = "exp_answerSource", index = 3)
    private String exp_answerSource;

    @ExcelProperty(value = "exp_matchDataSource", index = 4)
    private String exp_matchDataSource;

    @ExcelProperty(value = "exp_matchDataValue", index = 5)
    private String exp_matchDataValue;

    @ExcelProperty(value = "exp_hangUp", index = 6)
    private Boolean exp_hangUp;

    @ExcelProperty(value = "bz", index = 7)
    private String bz;

    @ExcelProperty(value = "act_answer", index = 8)
    private String act_answer;

    @ExcelProperty(value = "act_answerSource", index = 9)
    private String act_answerSource;

    @ExcelProperty(value = "act_matchDataSource", index = 10)
    private String act_matchDataSource;

    @ExcelProperty(value = "act_matchDataValue", index = 11)
    private String act_matchDataValue;

    @ExcelProperty(value = "act_hangUp", index = 12)
    private Boolean act_hangUp;

    /*比对期望值和实际值*/
    public Boolean compare() {
        //exp_answerList --> act_answerList
        if (!ListCompare.equals(this.exp_answerList, this.act_answerList)) {
            return false;
        }
        //exp_answerSource --> act_answerSource
        if ( this.exp_answerSource != null && !this.exp_answerSource.equals(this.act_answerSource)) {
            return false;
        }
        //exp_matchDataSource,exp_matchDataValue --> exp_matchDataSource,exp_matchDataValue
        if (!ListCompare.equals(this.exp_matchDataList, this.act_matchDataList)) {
            return false;
        }
        //exp_hangUp --> act_hangUp
        if (this.exp_hangUp != this.act_hangUp) {
            return false;
        }
        return true;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getAct_answerSource() {
        return act_answerSource;
    }

    public void setAct_answerSource(String act_answerSource) {
        this.act_answerSource = act_answerSource;
    }

    public String getAct_matchDataSource() {
        return act_matchDataSource;
    }

    public void setAct_matchDataSource(String act_matchDataSource) {
        this.act_matchDataSource = act_matchDataSource;
    }

    public String getAct_matchDataValue() {
        return act_matchDataValue;
    }

    public void setAct_matchDataValue(String act_matchDataValue) {
        this.act_matchDataValue = act_matchDataValue;
    }

    public Boolean getAct_hangUp() {
        return act_hangUp;
    }

    public void setAct_hangUp(Boolean act_hangUp) {
        this.act_hangUp = act_hangUp;
    }

    public List<String> getAct_answerList() {
        return act_answerList;
    }

    public void setAct_answerList(List<String> act_answerList) {
        this.act_answerList = act_answerList;
    }

    public List<MatchData> getAct_matchDataList() {
        return act_matchDataList;
    }

    public void setAct_matchDataList(List<MatchData> act_matchDataList) {
        this.act_matchDataList = act_matchDataList;
    }

    public String getAct_answer() {
        return act_answer;
    }

    public void setAct_answer(String act_answer) {
        this.act_answer = act_answer;
    }

    public int getRowIndex() {
        return rowIndex;
    }

    public void setRowIndex(int rowIndex) {
        this.rowIndex = rowIndex;
    }

    public List<String> getExp_answerList() {
        return exp_answerList;
    }

    public void setExp_answerList(List<String> exp_answerList) {
        this.exp_answerList = exp_answerList;
    }

    public List<MatchData> getExp_matchDataList() {
        return exp_matchDataList;
    }

    public void setExp_matchDataList(List<MatchData> exp_matchDataList) {
        this.exp_matchDataList = exp_matchDataList;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getExp_answer() {
        return exp_answer;
    }

    public void setExp_answer(String exp_answer) {
        this.exp_answer = exp_answer;
    }

    public String getExp_answerSource() {
        return exp_answerSource;
    }

    public void setExp_answerSource(String exp_answerSource) {
        this.exp_answerSource = exp_answerSource;
    }

    public String getExp_matchDataSource() {
        return exp_matchDataSource;
    }

    public void setExp_matchDataSource(String exp_matchDataSource) {
        this.exp_matchDataSource = exp_matchDataSource;
    }

    public String getExp_matchDataValue() {
        return exp_matchDataValue;
    }

    public void setExp_matchDataValue(String exp_matchDataValue) {
        this.exp_matchDataValue = exp_matchDataValue;
    }

    public Boolean getExp_hangUp() {
        return exp_hangUp;
    }

    public void setExp_hangUp(Boolean exp_hangUp) {
        this.exp_hangUp = exp_hangUp;
    }

    public String getBz() {
        return bz;
    }

    public void setBz(String bz) {
        this.bz = bz;
    }
}
