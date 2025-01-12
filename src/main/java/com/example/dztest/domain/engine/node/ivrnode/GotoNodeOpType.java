package com.example.dztest.domain.engine.node.ivrnode;

public enum GotoNodeOpType {
    /**
     * 退出场景
     */
    STOP("退出场景"),
    /**
     * 跳转指定子流程
     */
    FIXED_SUBFLOW("跳转指定子流程"),
    /**
     * 执行下一子流程
     */
    NEXT_SUBFLOW("执行下一子流程"),
    /**
     * 跳转至指定任务场景
     */
    FIXED_MAIN_FLOW("跳转至指定任务场景"),
    /**
     * 转人工
     */
    TRANSFER_TO_AGENT("转人工"),
    /**
     * 跳转到外部链接
     */
    GOTO_EXTERNAL_URL("跳转到外部链接"),
    /**
     * 跳转到语音IVR节点
     */
    AUDIO_IVR_NODE("跳转到语音IVR节点");

    private final String description;

    GotoNodeOpType(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
