package com.example.dztest.domain.engine.node;

import lombok.Data;

/**
 * 对话节点模块定义
 */
@Data
public class Dialog extends Condition{

    //回复话术配置
    private ReplyConfig replyConfig;

}
