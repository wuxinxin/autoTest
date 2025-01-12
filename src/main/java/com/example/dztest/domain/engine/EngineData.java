package com.example.dztest.domain.engine;

import lombok.Data;

import java.util.List;

@Data
public class EngineData {

    //外呼预制主流程
    private FlowData mainFlowData;

    //普通流程
    private List<FlowData> commonFlowList;

    //普通主流程
    private List<FlowData> commonMainFlowList;

    public FlowData getMainFlowData() {
        return mainFlowData;
    }

    public void setMainFlowData(FlowData mainFlowData) {
        this.mainFlowData = mainFlowData;
    }

    public List<FlowData> getCommonFlowList() {
        return commonFlowList;
    }

    public void setCommonFlowList(List<FlowData> commonFlowList) {
        this.commonFlowList = commonFlowList;
    }

    public List<FlowData> getCommonMainFlowList() {
        return commonMainFlowList;
    }

    public void setCommonMainFlowList(List<FlowData> commonMainFlowList) {
        this.commonMainFlowList = commonMainFlowList;
    }
}
