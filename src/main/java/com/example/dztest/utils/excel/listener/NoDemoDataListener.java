package com.example.dztest.utils.excel.listener;

import java.util.*;

import com.alibaba.excel.read.metadata.holder.ReadRowHolder;
import com.alibaba.excel.read.metadata.holder.ReadSheetHolder;
import com.example.dztest.domain.GlobalVar;
import com.example.dztest.utils.excel.base.*;
import com.example.dztest.utils.excel.exception.ExcelConfigException;
import lombok.SneakyThrows;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.alibaba.fastjson.JSON;

/**
 * @ClassName: NoDemoDataListener
 * @description: for the reason of that the column of every sheet is unknown
 * ,we use no demo data listener,and then,the demo data generate dynamic
 * @author: jian.ma@msxf.com
 * @create: 2022/06/25
 * @update: 2021/06/25
 **/
public class NoDemoDataListener extends AnalysisEventListener<Map<Integer, String>> {

    private static final Logger logger = LoggerFactory.getLogger(NoDemoDataListener.class);
    private static final int DATA_COUNT = 3000;
    Map<Integer, String> headMap = new HashMap<>();

    List<Map<Integer, String>> list = new ArrayList<Map<Integer, String>>();

    @SneakyThrows
    @Override
    public void invoke(Map<Integer, String> data, AnalysisContext context) {
        if ((data.size() != headMap.size())) {
            throw new ExcelConfigException("excel参数配置异常.");
        }
        list.add(data);
    }

    @SneakyThrows
    @Override
    public void doAfterAllAnalysed(AnalysisContext context) {
        List<Object> objects = new ArrayList<>();
        String sheetName = context.readSheetHolder().getSheetName();


        for (Map<Integer, String> map : list) {
            DemoData demoData = new DemoData();
            HashMap addMap = new HashMap();
            HashMap addValMap = new HashMap();

            for (Integer integer : map.keySet()) {
                addMap.put(headMap.get(integer), Class.forName("java.lang.Object"));
                addValMap.put(headMap.get(integer), map.get(integer));

                //add to my global map
                Map<String, Object> smap = new HashMap<>();
                smap.put(sheetName + "." + headMap.get(integer), map.get(integer));
                GlobalVar.BASE_DATA.add(smap);
            }
            Object obj2 = ClassUtil.dynamicClass(demoData, addMap, addValMap);
            objects.add(obj2);
        }

        for (int i = 0; i < objects.size(); i++) {
            logger.info("获取动态对象所有信息:{}", JSON.toJSONString(objects.get(i)));
        }
    }

    @Override
    public void invokeHeadMap(Map<Integer, String> headMap, AnalysisContext context) {
        this.headMap = headMap;
        logger.info("head map info：{}", headMap);
    }
}