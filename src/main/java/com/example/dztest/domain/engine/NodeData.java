package com.example.dztest.domain.engine;

import com.example.dztest.domain.engine.node.*;
import lombok.Data;

@Data
public class NodeData {

    //节点基础信息模
    private Base base;

    //条件节点模块信息
    private Condition condition;

    //IVR节点模块信息
    private IVR ivr;

    //对话节点模块定义
    private Dialog dialog;

    //跳转节点模块定义
    private GoTo goTo;

}
