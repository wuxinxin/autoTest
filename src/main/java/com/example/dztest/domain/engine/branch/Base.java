package com.example.dztest.domain.engine.branch;

import lombok.Data;

@Data
public class Base {
    //当前分支是否启用：true - 启用，false - 不启用"
    private Boolean enabled;

    //当前子流程ID
    private String flowId;

    //当前分支所属节点ID
    private String nodeId;

    //分支ID
    private String branchId;

    //分支名称
    private String branchName;

    /*
    * "分支类型：KEY_NAVIGATION - 按键导航，CONDITION - 条件判断，" +
    *       "INFO_COLLECTION - 信息采集，START_NODE_DEFAULT - 开始节点的默认分支，" +
    *        "DIALOG_NODE_NOT_JUDGED - 对话节点的不判断分支，DIALOG_NODE_UNKNOWN - " +
    *        "对话节点的未知分支，CONDITION_NODE_DEFAULT - 条件节点的默认分支，" +
    *        "REPEAT_KEY_NAVIGATION - 按键导航重听分支"
    */
    private BranchType branchType;

    //目标节点ID
    private String targetNodeId;

    //排序编号，从1开始
    private Integer sortNumber;
}
