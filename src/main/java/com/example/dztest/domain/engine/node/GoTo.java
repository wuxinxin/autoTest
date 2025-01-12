package com.example.dztest.domain.engine.node;

import com.example.dztest.domain.engine.node.ivrnode.GotoOp;
import lombok.Data;

/**
 * 跳转节点模块定义
 */
@Data
public class GoTo {
    //回复话术配置
    private ReplyConfig replyConfig;

    //下一步操作
    private GotoOp gotoOp;

}
