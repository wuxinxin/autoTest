package com.example.dztest.service.interfaces.kms;

/**
 * 机器人信息类型枚举类
 */
public enum RobotInfoClass {
    //任务流程
    TASK_PROCESS("taskProcess"),

    //任务意图
    TASK_PURPOSE("taskPurpose"),

    //问答知识
    QW_KNOWLEDGE("qwKnowledge"),

    //寒暄库
    HX_LIB("hxLib"),

    //停用词
    STOP_WORDS("stopWords"),

    //同义词
    SAME_WORDS("sameWords"),

    //实体管理
    ENTITY_MNG("entityMng"),

    //对话标签
    DIALOG_TAG("dialogTag"),

    //对话短信
    DIALOG_MSG("dialogMsg"),

    //黑名单规则
    BLACKLIST_RULE("blacklistRule"),

    //属性管理
    ATTRIBUTE_MNG("attributeMng"),

    //表格知识
    TABLE_KNOWLEDGE("tableKnowledge"),

    //机器人设置-基本信息
    ROBOT_SETTING_BASIC_INFO("robotSettingBasicInfo"),

    //机器人设置-引导语
    ROBOT_SETTING_INTRODUCTORY_LANGUAGE("robotSettingIntroductoryLanguage"),

    //机器人设置-转人工
    ROBOT_SETTING_ARTIFICIAL("robotSettingArtificial"),

    //机器人设置-知识设置
    ROBOT_SETTING_KNOWLEDGE_SET("robotSettingKnowledgeSet"),

    //机器人设置-语音设置
    ROBOT_SETTING_VOICE_SET("robotSettingVoiceSet"),

    //机器人设置-高级设置
    ROBOT_SETTING_HIGH_SET("robotSettingHighSet");

    private String value;

    RobotInfoClass(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    @Override
    public String toString() {
        return value;
    }
}