package com.example.dztest.extentreport.db.domain;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CaseInfo {
    /**
     * 主键id
     */
    private Long id;

    /**
     * 得助版本号
     */
    private String dzVersion;

    /**
     * 任务id
     */
    private Long taskId;

    /**
     * 用例类型，v1 v2
     */
    private String caseType;

    /**
     * 产品线
     */
    private String product;

    /**
     * 模块/功能
     */
    private String module;

    /**
     * 用例名称
     */
    private String caseName;

    /**
     * 用例步骤
     */
    private String caseStep;

    /**
     * 是否成功(0：失败，1：成功，2：跳过)；人工确认
     */
    private Byte result;

    /**
     * 开始时间
     */
    private Date startTime;

    /**
     * 结束时间
     */
    private Date endTime;

    /**
     * 作者
     */
    private String auth;

    /**
     * 转字符串集合
     */
    public List<Object> toList() {
        List<Object> list = new ArrayList<>();
        list.add(this.getDzVersion());
        list.add(this.getCaseType());
        list.add(this.getProduct());
        list.add(this.getModule());
        list.add(this.getCaseName());
        list.add(this.getCaseStep());
        list.add(this.getResult());
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        list.add(sdf.format(this.getStartTime()));
        list.add(sdf.format(this.getEndTime()));
        list.add(this.getAuth());
        return list;
    }

    /**
     * 写入飞书电子表格首行，转字符串集合
     */
    public List<String> toListCN() {
        List<String> list = new ArrayList<>();
        list.add("得助版本号");
        list.add("用例类型");
        list.add("产品线");
        list.add("模块/功能");
        list.add("用例名称");
        list.add("步骤");
        list.add("是否成功");
        list.add("开始时间");
        list.add("结束时间");
        list.add("作者");
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

    public Long getTaskId() {
        return taskId;
    }

    public void setTaskId(Long taskId) {
        this.taskId = taskId;
    }

    public String getCaseType() {
        return caseType;
    }

    public void setCaseType(String caseType) {
        this.caseType = caseType == null ? null : caseType.trim();
    }

    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        this.product = product == null ? null : product.trim();
    }

    public String getModule() {
        return module;
    }

    public void setModule(String module) {
        this.module = module == null ? null : module.trim();
    }

    public String getCaseName() {
        return caseName;
    }

    public void setCaseName(String caseName) {
        this.caseName = caseName == null ? null : caseName.trim();
    }

    public String getCaseStep() {
        return caseStep;
    }

    public void setCaseStep(String caseStep) {
        this.caseStep = caseStep == null ? null : caseStep.trim();
    }

    public Byte getResult() {
        return result;
    }

    public void setResult(Byte result) {
        this.result = result;
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

    public String getAuth() {
        return auth;
    }

    public void setAuth(String auth) {
        this.auth = auth == null ? null : auth.trim();
    }
}