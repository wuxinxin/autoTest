package com.example.dztest.domain.engine.node;


import com.example.dztest.domain.engine.branch.BranchData;
import lombok.Data;

import java.util.List;

/**
 * 条件节点模块定义
 */
@Data
public class Condition {
    //条件分支列表
    protected List<BranchData> branches;
}
