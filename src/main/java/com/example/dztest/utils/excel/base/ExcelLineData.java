package com.example.dztest.utils.excel.base;

import lombok.Data;

import java.util.List;

/**
 * @ClassName: ExcelLineData
 * @description: ExcelLineData
 * @author: jian.ma@msxf.com
 * @create: 2022/06/30
 * @update: 2022/06/30
 **/


@Data
public class ExcelLineData {
    private int lineNumber;

    private int colSum;

    private List<String> colData;
}
