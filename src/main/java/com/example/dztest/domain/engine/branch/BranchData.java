package com.example.dztest.domain.engine.branch;

import com.example.dztest.domain.engine.node.Condition;
import lombok.Data;

@Data
public class BranchData {

    //分支基础信息模块
    private Base base;

    //条件分支模块信息
    private Condition condition;

}
