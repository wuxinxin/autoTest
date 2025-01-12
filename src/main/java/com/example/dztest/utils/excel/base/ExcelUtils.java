package com.example.dztest.utils.excel.base;

import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import java.io.*;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @ClassName: ExcelUtils
 * @description: ExcelUtils
 * @author: jian.ma@msxf.com
 * @create: 2022/06/30
 * @update: 2022/06/30
 **/


public class ExcelUtils {
    private static HSSFWorkbook wb;
    private static HSSFSheet sheet;
    private static HSSFRow row;
    private static XSSFWorkbook wbx;
    private static XSSFSheet sheetx;
    private static XSSFRow rowx;

    public static ExcelData readExcel(String filePath) {
        InputStream is = null;

        Resource resource = new ClassPathResource(filePath);
        File file = null;
        try {
            file = resource.getFile();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

//        File file = new File(filePath);
        System.out.println("readExcel filepath is " + filePath);
        try {
            is = new FileInputStream(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        ExcelData excelData = new ExcelData();
        try {
            if (filePath.substring(filePath.length() - 5, filePath.length()).equals(".xlsx")) {
                wbx = new XSSFWorkbook(is);
                return readExcelx(wbx, file.getName());
            }
            wb = new HSSFWorkbook(is);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Integer sheetNum = wb.getNumberOfSheets();
        excelData.setSheetSum(sheetNum);
        excelData.setFileName(file.getName());

        List<ExcelSheetData> sheetDatas = new ArrayList<>();
        for (int i = 0; i < sheetNum; i++) {
            ExcelSheetData sheetData = new ExcelSheetData();
            sheet = wb.getSheetAt(i);
            sheetData.setLineSum(sheet.getPhysicalNumberOfRows());
            sheetData.setSheetName(sheet.getSheetName());

            List<ExcelLineData> lineDatas = readExcelContentBySheet(sheet);
            sheetData.setLineData(lineDatas);
            sheetDatas.add(sheetData);
        }
        excelData.setSheetData(sheetDatas);
        return excelData;
    }

    private static ExcelData readExcelx(XSSFWorkbook wbx, String fileName) {

        ExcelData excelData = new ExcelData();
        Integer sheetNum = wbx.getNumberOfSheets();
        excelData.setSheetSum(sheetNum);
        excelData.setFileName(fileName);

        List<ExcelSheetData> sheetDatas = new ArrayList<>();
        for (int i = 0; i < sheetNum; i++) {
            ExcelSheetData sheetData = new ExcelSheetData();
            sheetx = wbx.getSheetAt(i);
            sheetData.setSheetName(sheetx.getSheetName());
            sheetData.setLineSum(sheetx.getPhysicalNumberOfRows());
            List<ExcelLineData> lineDatas = readExcelContentBySheetx(sheetx);
            sheetData.setLineData(lineDatas);
            sheetDatas.add(sheetData);
        }
        excelData.setSheetData(sheetDatas);
        return excelData;
    }


    private static List<ExcelLineData> readExcelContentBySheet(HSSFSheet sheet) {
        List<ExcelLineData> lineDatas = new ArrayList<>();
        int rowNum = sheet.getLastRowNum();

        for (int i = 0; i <= rowNum; i++) {
            int j = 0;
            row = sheet.getRow(i);
            if (Objects.isNull(row)) {
                continue;
            }

            int colNum = row.getLastCellNum();
            ExcelLineData lineData = new ExcelLineData();
            List<String> colData = new ArrayList<>();
            lineData.setColSum(colNum);
            while (j < colNum) {
                String value = getCellValue(row.getCell(j)).trim();
                colData.add(value);
                j++;
            }
            lineData.setColData(colData);
            lineDatas.add(lineData);
        }

        return lineDatas;
    }

    private static List<ExcelLineData> readExcelContentBySheetx(XSSFSheet sheetx) {
        List<ExcelLineData> lineDatas = new ArrayList<>();

        int rowNum = sheetx.getLastRowNum();
        for (int i = 0; i <= rowNum; i++) {
            int j = 0;
            rowx = sheetx.getRow(i);
            if (Objects.isNull(rowx)) {
                continue;
            }

            int colNum = rowx.getLastCellNum();
            ExcelLineData lineData = new ExcelLineData();
            List<String> colData = new ArrayList<>();
            lineData.setColSum(colNum);
            while (j < colNum) {
                String value = getCellValuex(rowx.getCell(j)).trim();
                colData.add(value);
                j++;
            }
            lineData.setColData(colData);
            lineDatas.add(lineData);
        }

        return lineDatas;
    }

    public static String getCellValue(Cell cell) {
        if (Objects.isNull(cell)) {
            return "";
        }

        String value = "";
        switch (cell.getCellType()) {
            case NUMERIC:
                if (HSSFDateUtil.isCellDateFormatted(cell)) {
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                    value = sdf.format(HSSFDateUtil.getJavaDate(cell.getNumericCellValue())).toString();
                    break;
                } else {
                    value = new DecimalFormat("0").format(cell.getNumericCellValue());
                }
                break;
            case STRING:
                value = cell.getStringCellValue();
                break;
            case BOOLEAN:
                value = cell.getBooleanCellValue() + "";
                break;

            case BLANK:
                value = "";
                break;
            case ERROR:
                value = "非法字符";
                break;
            default:
                value = "未知类型";
                break;
        }
        return value;
    }

    private static String getCellValuex(XSSFCell cellx) {
        if (Objects.isNull(cellx)) {
            return "";
        }

        String value = "";
        switch (cellx.getCellType()) {
            case NUMERIC:
                if (HSSFDateUtil.isCellDateFormatted(cellx)) {
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                    value = sdf.format(HSSFDateUtil.getJavaDate(cellx.getNumericCellValue())).toString();
                    break;
                } else {
                    value = new DecimalFormat("0").format(cellx.getNumericCellValue());
                }
                break;
            case STRING:
                value = cellx.getStringCellValue();
                break;
            case BOOLEAN:
                value = cellx.getBooleanCellValue() + "";
                break;
            case BLANK:
                value = "";
                break;
            case ERROR:
                value = "非法字符";
                break;
            default:
                value = "未知类型";
                break;
        }
        return value;
    }
}
