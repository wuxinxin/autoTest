package com.example.dztest.domain.engine;

import lombok.Data;

import java.util.Map;

@Data
public class SubFlowData {

    //所属主流程id
    private String mainFlowId;

    //流程id
    private String flowId;

    //流程名字
    private String flowName;

    //排序编号
    private Integer sortNumber;

    //是否为触发子流程
    private Boolean triggerSubflow;

    //标识子流程是否已初始化
    private Boolean initialized;

    //子流程是否有效
    private boolean valid;

    //节点字典：key - nodeId，value - 节点
    private Map<String, NodeData> nodes;

}
