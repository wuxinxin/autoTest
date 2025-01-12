package com.example.dztest.domain.engine.node;

import lombok.Data;

/*
* 节点基础信息模块
* */
@Data
public class Base {

    //当前节点所属子流程ID
    private String flowId;

    //画布节点ID
    private String nodeId;

    //画布节点名称
    private String nodeName;

    //节点唯一ID
    private String nodeUniqueId;

    /**
    * 节点类型：START_NODE - 开始节点，DIALOG_NODE - 对话节点，
    * CONDITION_NODE - 条件节点，GOTO_NODE - 跳转节点，IVR_NODE - IVR节点
    */
    private String nodeType;

    //节点角色：HEAD - 头，TAIL - 尾，COMMON - 普通
    private String nodeRole;

    public String getFlowId() {
        return flowId;
    }

    public void setFlowId(String flowId) {
        this.flowId = flowId;
    }

    public String getNodeId() {
        return nodeId;
    }

    public void setNodeId(String nodeId) {
        this.nodeId = nodeId;
    }

    public String getNodeName() {
        return nodeName;
    }

    public void setNodeName(String nodeName) {
        this.nodeName = nodeName;
    }

    public String getNodeUniqueId() {
        return nodeUniqueId;
    }

    public void setNodeUniqueId(String nodeUniqueId) {
        this.nodeUniqueId = nodeUniqueId;
    }

    public String getNodeType() {
        return nodeType;
    }

    public void setNodeType(String nodeType) {
        this.nodeType = nodeType;
    }

    public String getNodeRole() {
        return nodeRole;
    }

    public void setNodeRole(String nodeRole) {
        this.nodeRole = nodeRole;
    }
}
