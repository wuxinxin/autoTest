package com.example.dztest.service.interfaces.kms.robot_entity.listener;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.read.listener.ReadListener;
import com.alibaba.excel.util.ListUtils;
import com.alibaba.fastjson.JSON;
import com.example.dztest.domain.GlobalVar;
import com.example.dztest.service.interfaces.kms.robot_entity.IntroductionLanguage;
import com.example.dztest.service.interfaces.kms.robot_entity.ToManualRule;
import com.example.dztest.utils.SpringContextUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Component(value = "newOutL2")
public class ToManualRuleListener implements ReadListener<ToManualRule> {
    private String flag = "";
    private static final int BATCH_COUNT = 100;
    private List<ToManualRule> cachedDataList = ListUtils.newArrayListWithExpectedSize(BATCH_COUNT);
    private ToManualRule ob;
    private List<String> sheetDataList = new ArrayList<>();

    public ToManualRuleListener() {
        ob = new ToManualRule();
    }

    public ToManualRuleListener(String flag) {
        ob = new ToManualRule();
        this.flag = flag;
    }

    public ToManualRuleListener(ToManualRule ob) {
        this.ob = ob;
    }


    @Override
    public void invoke(ToManualRule data, AnalysisContext context) {
        log.info(this.getClass() + "解析到一条数据:{}", JSON.toJSONString(data));
        cachedDataList.add(data);

        String name = context.readWorkbookHolder().getFile().getName();

        if (name.contains("exp")) {
            GlobalVar.ROBOT_INFO_EXPECT.add(JSON.toJSONString(data));
        }

        if (name.contains("act")) {
            GlobalVar.ROBOT_INFO_ACTUAL.add(JSON.toJSONString(data));
        }

        // 达到BATCH_COUNT了，需要去存储一次数据库，防止数据几万条数据在内存，容易OOM
        if (cachedDataList.size() >= BATCH_COUNT) {
            // 存储完成清理 list
            cachedDataList = ListUtils.newArrayListWithExpectedSize(BATCH_COUNT);
        }
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext context) {
        log.info("所有数据解析完成！");
    }
}