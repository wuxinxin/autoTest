package com.example.dztest.service.interfaces.kms.robot_entity.listener;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.read.listener.ReadListener;
import com.alibaba.excel.util.ListUtils;
import com.alibaba.fastjson.JSON;
import com.example.dztest.domain.GlobalVar;
import com.example.dztest.service.interfaces.kms.robot_entity.TaskIntention;
import com.example.dztest.service.interfaces.kms.robot_entity.TaskProcess;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Component(value = "newOutL0")
public class TaskIntentionListener implements ReadListener<TaskIntention> {
    private String flag = "";
    private static final int BATCH_COUNT = 100;
    private List<TaskIntention> cachedDataList = ListUtils.newArrayListWithExpectedSize(BATCH_COUNT);
    private TaskIntention ob;
    private List<String> sheetDataList = new ArrayList<>();

    public TaskIntentionListener() {
        ob = new TaskIntention();
    }

    public TaskIntentionListener(String flag) {
        ob = new TaskIntention();
        this.flag = flag;
    }

    public TaskIntentionListener(TaskIntention ob) {
        this.ob = ob;
    }


    @Override
    public void invoke(TaskIntention data, AnalysisContext context) {
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