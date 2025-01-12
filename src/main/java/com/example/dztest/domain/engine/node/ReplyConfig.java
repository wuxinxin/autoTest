package com.example.dztest.domain.engine.node;

import lombok.Data;

import java.util.List;

/**
 * 回复话术配置定义
 */
@Data
public class ReplyConfig {

    //标准回复话术
    private String standardReply;

    //标准回复话术
    private List<String> standardReplies;

    //重复回复话术
    private List<String> repeatReplies;

    //跳出场景恢复引导回话
    private String resumeReply;

}
