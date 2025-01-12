package com.example.dztest.extentreport.db.domain;

public class CaseInfoTest {
    /**
     * 主键id
     */
    private Long id;

    /**
     * 得助版本号
     */
    private String dzVersion;

    /**
     * 是否成功(0：失败，1：成功，2：跳过)；人工确认
     */
    private Byte result;

    /**
     * 是否成功(0：失败，1：成功，2：跳过)；人工确认
     */
    private Boolean isDeleted;

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

    public Byte getResult() {
        return result;
    }

    public void setResult(Byte result) {
        this.result = result;
    }

    public Boolean getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(Boolean isDeleted) {
        this.isDeleted = isDeleted;
    }
}