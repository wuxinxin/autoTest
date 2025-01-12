package com.example.dztest.domain.engine.branch;

public enum BranchType {
    /**
     * 按键导航
     */
    KEY_NAVIGATION(false),
    /**
     * 条件判断
     */
    CONDITION(false),
    /**
     * 选项节点条件判断
     */
    OPTIONAL_NODE_CONDITION(false),
    /**
     * 选项列表可配置条件(跳过此节点条件)
     */
    OPTIONAL_NODE_PRECONDITION(false),
    /**
     * 信息采集
     */
    INFO_COLLECTION(false),
    /**
     * 开始节点的默认分支
     */
    START_NODE_DEFAULT(true),
    /**
     * 对话节点的不判断分支
     */
    DIALOG_NODE_NOT_JUDGED(true),
    /**
     * 对话节点的未知分支
     */
    DIALOG_NODE_UNKNOWN(true),
    /**
     * 条件节点的默认分支
     */
    CONDITION_NODE_DEFAULT(true),
    /**
     * 按键导航重听分支
     */
    REPEAT_KEY_NAVIGATION(true),
    /**
     * 其它按键分支
     */
    OTHER_KEY_NAVIGATION(true),
    /**
     * 按键导航重听分支
     */
    ANSWER_ON_CLICK(true),
    /**
     * 答案为空
     */
    EMPTY_ANSWER(true),
    /**
     * 答案生成异常
     */
    FALLBACK_ANSWER(true),
    ;

    private final boolean preset;

    BranchType(boolean preset) {
        this.preset = preset;
    }

    public boolean isPreset() {
        return preset;
    }
}
