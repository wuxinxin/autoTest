package com.example.dztest.service.interfaces.kms.robot_entity.listener;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.read.listener.ReadListener;
import com.alibaba.excel.util.ListUtils;
import com.alibaba.fastjson.JSON;
import com.example.dztest.domain.GlobalVar;
import com.example.dztest.service.interfaces.kms.robot_entity.AttributeMng;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Slf4j
@EasyExcelListener
@Component(value = "l5")
public class AttributeMngListener implements ReadListener<AttributeMng> {
    private String flag = "";
    private static final int BATCH_COUNT = 100;
    private List<AttributeMng> cachedDataList = ListUtils.newArrayListWithExpectedSize(BATCH_COUNT);
    private AttributeMng ob;
    private List<String> sheetDataList = new ArrayList<>();

    public AttributeMngListener() {
        ob = new AttributeMng();
    }

    public AttributeMngListener(String flag) {
        ob = new AttributeMng();
        this.flag = flag;
    }

    public AttributeMngListener(AttributeMng ob) {
        this.ob = ob;
    }

    @Override
    public void invoke(AttributeMng data, AnalysisContext context) {
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