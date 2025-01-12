package com.example.dztest.domain.engine;

import lombok.Data;

import java.util.SortedSet;

@Data
public class FlowData {

    //0-普通流程 2-普通流程 1-预置主流程 3-普通主流程
    private int type;

    //线上状态：true - 启用，false - 停用
    private Boolean active;

    //流程id
    private String flowId;

    //流程名字
    private String flowName;

    //排序编号
    private Integer sortNumber;

    //按排序编号排序的子流程列表
    private SortedSet<SubFlowData> subflows;



    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getFlowId() {
        return flowId;
    }

    public void setFlowId(String flowId) {
        this.flowId = flowId;
    }

    public String getFlowName() {
        return flowName;
    }

    public void setFlowName(String flowName) {
        this.flowName = flowName;
    }
}
