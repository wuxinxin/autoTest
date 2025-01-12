package com.example.dztest.extentreport.db.domain;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CaseTask {
    /**
     * 主键id
     */
    private Long id;

    /**
     * 得助版本号
     */
    private String dzVersion;

    /**
     * 执行环境
     */
    private String runEnv;

    /**
     * 脚本分支
     */
    private String branch;

    /**
     * 开始时间
     */
    private Date startTime;

    /**
     * 结束时间
     */
    private Date endTime;

    /**
     * 执行用例数
     */
    private Integer caseNum;

    /**
     * 成功用例数
     */
    private Integer caseSuccessNum;

    /**
     * 失败用例数
     */
    private Integer caseFailedNum;

    /**
     * 跳过用例数
     */
    private Integer caseSkippedNum;

    /**
     * 成功率
     */
    private BigDecimal successRate;

    /**
     * 测试报告地址
     */
    private String testReport;

    /**
     * 是否有效(0：否，1：是)；人工确认
     */
    private Byte isEffective;

    /**
     * 备注
     */
    private String notes;

    /**
     * 转字符串集合
     * @return
     */
    public List<Object> toList() {
        List<Object> list = new ArrayList<>();
        list.add(this.getDzVersion());
        list.add(this.getRunEnv());
        list.add(this.getBranch());
        list.add(this.getCaseNum());
        list.add(this.getCaseSuccessNum());
        list.add(this.getCaseFailedNum());
        list.add(this.getCaseSkippedNum());
        list.add(this.getSuccessRate() + "%");
        list.add(this.getTestReport());
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        list.add(sdf.format(this.getStartTime()));
        list.add(sdf.format(this.getEndTime()));
        list.add("是");
        list.add(this.getNotes());
        return list;
    }

    /**
     * 写入飞书电子表格首行，转字符串集合
     * @return
     */
    public List<String> toListCN() {
        List<String> list = new ArrayList<>();
        list.add("得助版本号");
        list.add("执行环境");
        list.add("脚本分支");
        list.add("执行用例数");
        list.add("成功用例数");
        list.add("失败用例数");
        list.add("跳过用例数");
        list.add("成功率");
        list.add("测试报告");
        list.add("开始时间");
        list.add("结束时间");
        list.add("是否有效");
        list.add("备注");
        return list;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDzVersion() {
        return dzVersion;
    }

    public void setDzVersion(String dzVersion) {
        this.dzVersion = dzVersion == null ? null : dzVersion.trim();
    }

    public String getRunEnv() {
        return runEnv;
    }

    public void setRunEnv(String runEnv) {
        this.runEnv = runEnv == null ? null : runEnv.trim();
    }

    public String getBranch() {
        return branch;
    }

    public void setBranch(String branch) {
        this.branch = branch == null ? null : branch.trim();
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public Integer getCaseNum() {
        return caseNum;
    }

    public void setCaseNum(Integer caseNum) {
        this.caseNum = caseNum;
    }

    public Integer getCaseSuccessNum() {
        return caseSuccessNum;
    }

    public void setCaseSuccessNum(Integer caseSuccessNum) {
        this.caseSuccessNum = caseSuccessNum;
    }

    public Integer getCaseFailedNum() {
        return caseFailedNum;
    }

    public void setCaseFailedNum(Integer caseFailedNum) {
        this.caseFailedNum = caseFailedNum;
    }

    public Integer getCaseSkippedNum() {
        return caseSkippedNum;
    }

    public void setCaseSkippedNum(Integer caseSkippedNum) {
        this.caseSkippedNum = caseSkippedNum;
    }

    public BigDecimal getSuccessRate() {
        return successRate;
    }

    public void setSuccessRate(BigDecimal successRate) {
        this.successRate = successRate;
    }

    public String getTestReport() {
        return testReport;
    }

    public void setTestReport(String testReport) {
        this.testReport = testReport == null ? null : testReport.trim();
    }

    public Byte getIsEffective() {
        return isEffective;
    }

    public void setIsEffective(Byte isEffective) {
        this.isEffective = isEffective;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes == null ? null : notes.trim();
    }
}