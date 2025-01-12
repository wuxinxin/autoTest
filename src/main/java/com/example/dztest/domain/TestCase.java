package com.example.dztest.domain;

import com.example.dztest.annotation.ExcelAnnotation;

public class TestCase {
    @ExcelAnnotation(columnIndex = 0, columnName = "case_id")
    private Integer caseId;
    @ExcelAnnotation(columnIndex = 1, columnName = "node")
    private String node;
    @ExcelAnnotation(columnIndex = 2, columnName = "case_name")
    private String caseName;
    @ExcelAnnotation(columnIndex = 3, columnName = "case_description")
    private String caseDescription;
    @ExcelAnnotation(columnIndex = 4, columnName = "run_flag")
    private String runFlag;
    @ExcelAnnotation(columnIndex = 5, columnName = "login_account")
    private String login_account;
    @ExcelAnnotation(columnIndex = 6, columnName = "login_pwd")
    private String login_pwd;

    public Integer getCaseId() {
        return caseId;
    }

    public void setCaseId(Integer caseId) {
        this.caseId = caseId;
    }

    public String getNode() {
        return node;
    }

    public void setNode(String node) {
        this.node = node;
    }

    public String getCaseName() {
        return caseName;
    }

    public void setCaseName(String caseName) {
        this.caseName = caseName;
    }

    public String getCaseDescription() {
        return caseDescription;
    }

    public void setCaseDescription(String caseDescription) {
        this.caseDescription = caseDescription;
    }

    public String getRunFlag() {
        return runFlag;
    }

    public void setRunFlag(String runFlag) {
        this.runFlag = runFlag;
    }

    public String getLoginAccount() {
        return login_account;
    }

    public void setLoginAccount(String login_account) {
        this.login_account = login_account;
    }

    public String getLoginPwd() {
        return login_pwd;
    }

    public void setLoginPwd(String login_pwd) {
        this.login_pwd = login_pwd;
    }
}
