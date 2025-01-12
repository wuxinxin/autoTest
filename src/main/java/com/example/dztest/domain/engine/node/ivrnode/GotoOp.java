package com.example.dztest.domain.engine.node.ivrnode;

import lombok.Data;

import java.util.List;

/**
 * 跳转节点下一步操作定义
 */
@Data
public class GotoOp {

    /*
    * 下一操作类型：STOP - 退出场景，" +
            "FIXED_SUBFLOW - 跳转指定子流程，NEXT_SUBFLOW - 执行下一子流程，" +
            "FIXED_MAIN_FLOW - 跳转至指定任务场景，TRANSFER_TO_AGENT - 转人工
    * */
    private GotoNodeOpType opType;

    //是否语音结束通话挂机
    private Boolean audioStopOpToHangUp;

    //指定子流程名称
    private String fixedSubflowName;

    //指定主流程名称
    private String fixedMainFlowName;

    //指定子流程ID
    private String fixedSubflowId;

    //指定主流程ID
    private String fixedMainFlowId;

    //转人工时机器人支持的场景范围
    private List<RobotSceneScope> transferRobotSceneScopes;

    //语音转人工方式：INBOUND - 呼入，OUTBOUND - 呼出
    private TransferToAudioWay transferToAudioWay;

    //语音转人工技能组ID
    private String transferToAudioAgentId;

    //语音转人工技能组优先级
    private Integer transferToAudioPriority;

}
