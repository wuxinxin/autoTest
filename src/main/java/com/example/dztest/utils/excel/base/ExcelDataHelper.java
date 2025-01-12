package com.example.dztest.utils.excel.base;

import cn.hutool.core.util.StrUtil;
import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelReader;
import com.alibaba.excel.read.metadata.ReadSheet;
import com.example.dztest.domain.GlobalVar;
import com.example.dztest.utils.excel.exception.ExcelConfigException;
import com.example.dztest.utils.excel.listener.NoDemoDataListener;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ClassName: ExcelDataHelper
 * @description: get excel sheet list,excel data.
 * @author: jian.ma@msxf.com
 * @create: 2022/06/25
 * @update: 2021/06/25
 **/


public abstract class ExcelDataHelper {
    /**
     * 通过excel文件获取sheetName的list
     *
     * @param inputStream
     * @return
     */
    public static List<ReadSheet> listSheet(InputStream inputStream) {
        if (inputStream == null) {
            throw new RuntimeException("inputStream cannot be null");
        }

        ExcelReader build = EasyExcel.read(inputStream)
                .build();

        List<ReadSheet> readSheets = build.excelExecutor()
                .sheetList();

        return readSheets;
    }

    public static void getExcelData(String path) {
        List<ReadSheet> sheets = EasyExcel.read(path).build().excelExecutor().sheetList();

        sheets.forEach(s -> {
            /**
             * headRowNumber(1)
             * 1 - This Sheet has one row head , this is the default
             */
            EasyExcel.read(path, new NoDemoDataListener()).headRowNumber(1)
                    .sheet(s.getSheetName()).doRead();
        });
    }

    public static void getExcelDataByColumnWay(String file) {
        ExcelData excelData = ExcelUtils.readExcel(file);

        try {
            for (ExcelSheetData excelSheetData : excelData.getSheetData()) {
                for (ExcelLineData excelLineData : excelSheetData.getLineData()) {
                    Map<String, Object> smap = new HashMap<>();
                    smap.put(excelSheetData.getSheetName()
                            + "." + excelLineData.getColData().get(0), excelLineData.getColData().get(1));
                    GlobalVar.BASE_DATA.add(smap);
                }
            }
        } catch (Exception e) {
            System.out.println("In getExcelDataByColumnWay,exception is ==>" + e);
            throw new ExcelConfigException("Some value of key missed,please check");
        }
    }
}
