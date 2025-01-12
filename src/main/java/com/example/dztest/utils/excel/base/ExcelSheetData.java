package com.example.dztest.utils.excel.base;

import lombok.Data;

import java.util.List;

/**
 * @ClassName: ExcelSheetData
 * @description: ExcelSheetData
 * @author: jian.ma@msxf.com
 * @create: 2022/06/30
 * @update: 2022/06/30
 **/


@Data
public class ExcelSheetData implements SheetElement {
    private String sheetName;

    private int lineSum;

    private List<ExcelLineData> lineData;
}
