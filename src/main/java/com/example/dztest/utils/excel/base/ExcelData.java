package com.example.dztest.utils.excel.base;

import lombok.Data;

import java.util.List;

@Data
public class ExcelData {
    private int sheetSum;

    private String fileName;

    private List<ExcelSheetData> sheetData;
}