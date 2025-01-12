package com.example.dztest.utils;

import com.example.dztest.annotation.ExcelAnnotation;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * Description: excel工具类（泛型类）
 * <p>
 * Author: zhenlin.he
 * Date: 2019/8/10 9:48
 */
public class ExcelUtil {
    private static final Logger LOGGER = LoggerFactory.getLogger(ExcelUtil.class);

    /**
     * 日期格式化
     */
    private static final SimpleDateFormat sdf = new SimpleDateFormat("yyy-MM-dd HH:mm");
    /**
     * 整数格式化,取所有整数部分
     */
    private static final DecimalFormat df = new DecimalFormat("0");

    /**
     * 读取excel
     *
     * @param excelPath
     * @param tClass
     * @param <T>
     * @return
     */
    public static <T> List<T> readExcel(String excelPath, String sheetName, Class<T> tClass) {
        // 存储excel数据
        List<T> tList = new ArrayList<>();
        Workbook workbook = null;
        File file = new File(excelPath);
        try {
            workbook = WorkbookFactory.create(file);
        } catch (IOException e) {
            LOGGER.error(e.getMessage());
        }
        // 获取sheet
        assert workbook != null;
        Sheet sheet = StringUtils.isBlank(sheetName) ? workbook.getSheetAt(0) : workbook.getSheet(sheetName);
        // 获取行总数
        int rows = sheet.getLastRowNum() + 1;
        // 获取类所有属性
        Field[] fields = tClass.getDeclaredFields();
        for (int i = 1; i < rows; i++) {
//            遍历行
            Row row = sheet.getRow(i);
            try {
                T obj = tClass.newInstance();
//                遍历列单元格
                Arrays.stream(fields).forEach(f -> {
                    f.setAccessible(true);
                    // 判断是否是注解
                    if (f.isAnnotationPresent(ExcelAnnotation.class)) {
                        // 获取注解
                        ExcelAnnotation excelAnnotation = f.getAnnotation(ExcelAnnotation.class);
                        // 获取列索引
                        int columnIndex = excelAnnotation.columnIndex();
                        // 获取单元格
                        Cell cell = row.getCell(columnIndex);
                        // 设置属性
                        setFieldValue(obj, f, cell);
                    }
                });
                // 添加到集合中
                tList.add(obj);
            } catch (InstantiationException | IllegalAccessException e1) {
                e1.printStackTrace();
            }
        }
        try {
            //释放资源
            workbook.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return tList;
    }

    /**
     * 读取excel
     *
     * @param excelPath
     * @param tClass
     * @param <T>
     * @return
     */
    public static <T> List<T> readFile(MultipartFile file, String sheetName, Class<T> tClass) {
        // 存储excel数据
        List<T> tList = new ArrayList<>();

        //获得excel文件的名称
        String filename = file.getOriginalFilename();
        Workbook workbook = null;
//        File file2 = new File(file);
        try {
            //获得excel文件的io流
            InputStream is = file.getInputStream();
//            workbook = WorkbookFactory.create(file);
            //跟进不同文件后缀（xls和xlsx）获取不同的workbook实现类
            if(filename.endsWith("xls")){
                workbook = new HSSFWorkbook(is);
            } else if(filename.endsWith("xlsx")){
                workbook = new XSSFWorkbook(is);
            }
        } catch (IOException e) {
            LOGGER.error(e.getMessage());
        }
        // 获取sheet
        assert workbook != null;
        Sheet sheet = StringUtils.isBlank(sheetName) ? workbook.getSheetAt(0) : workbook.getSheet(sheetName);
        // 获取行总数
        int rows = sheet.getLastRowNum() + 1;
        // 获取类所有属性
        Field[] fields = tClass.getDeclaredFields();
        for (int i = 1; i < rows; i++) {
//            遍历行
            Row row = sheet.getRow(i);
            try {
                T obj = tClass.newInstance();
//                遍历列单元格
                Arrays.stream(fields).forEach(f -> {
                    f.setAccessible(true);
                    // 判断是否是注解
                    if (f.isAnnotationPresent(ExcelAnnotation.class)) {
                        // 获取注解
                        ExcelAnnotation excelAnnotation = f.getAnnotation(ExcelAnnotation.class);
                        // 获取列索引
                        int columnIndex = excelAnnotation.columnIndex();
                        // 获取单元格
                        Cell cell = row.getCell(columnIndex);
                        // 设置属性
                        setFieldValue(obj, f, cell);
                    }
                });
                // 添加到集合中
                tList.add(obj);
            } catch (InstantiationException | IllegalAccessException e1) {
                e1.printStackTrace();
            }
        }
        try {
            //释放资源
            workbook.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return tList;
    }

    /**
     * 设置属性
     *
     * @param obj  目标对象
     * @param f    属性
     * @param cell 单元格
     */
    private static void setFieldValue(Object obj, Field f, Cell cell) {
        try {
            switch (cell.getCellType()) {
                case STRING: // 字符串类型
                    f.set(obj, cell.getStringCellValue());
                    break;
                case BOOLEAN: // 布尔类型
                    f.setBoolean(obj, cell.getBooleanCellValue());
                    break;
                case NUMERIC:
                    //数值类型-默认取出来是double，这里需要单独处理int、data类型
                    if ("m/d/yy".equals(cell.getCellStyle().getDataFormatString())) {
                        f.set(obj, sdf.format(cell.getDateCellValue()));
                    } else if (HSSFDateUtil.isCellDateFormatted(cell)) {
                        Date date = cell.getDateCellValue();
                        f.set(obj, sdf.format(date));
                    } else {
                        f.set(obj, Integer.parseInt(df.format(cell.getNumericCellValue())));
                    }
                    break;
                case ERROR: // 错误
                    break;
                case BLANK: // 空值
                    break;
                case _NONE: //
                    break;
                case FORMULA:
                    break;
                default:
                    break;
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }
}
