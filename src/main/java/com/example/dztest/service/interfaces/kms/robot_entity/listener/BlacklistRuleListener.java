package com.example.dztest.service.interfaces.kms.robot_entity.listener;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.read.listener.ReadListener;
import com.alibaba.excel.util.ListUtils;
import com.alibaba.fastjson.JSON;
import com.example.dztest.domain.GlobalVar;
import com.example.dztest.service.interfaces.kms.robot_entity.AttributeMng;
import com.example.dztest.service.interfaces.kms.robot_entity.BlacklistRule;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Component(value = "l11")
public class BlacklistRuleListener implements ReadListener<BlacklistRule> {
    private String flag = "";
    private static final int BATCH_COUNT = 100;
    private List<BlacklistRule> cachedDataList = ListUtils.newArrayListWithExpectedSize(BATCH_COUNT);
    private BlacklistRule ob;
    private List<String> sheetDataList = new ArrayList<>();

    public BlacklistRuleListener() {
        ob = new BlacklistRule();
    }

    public BlacklistRuleListener(String flag) {
        ob = new BlacklistRule();
        this.flag = flag;
    }

    public BlacklistRuleListener(BlacklistRule ob) {
        this.ob = ob;
    }

    @Override
    public void invoke(BlacklistRule data, AnalysisContext context) {
        log.info(this.getClass() + "解析到一条数据:{}", JSON.toJSONString(data));
        cachedDataList.add(data);

        String name = context.readWorkbookHolder().getFile().getName();

        if (name.contains("expect")) {
            GlobalVar.ROBOT_INFO_EXPECT.add(JSON.toJSONString(data));
        }

        if (name.contains("actual")) {
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