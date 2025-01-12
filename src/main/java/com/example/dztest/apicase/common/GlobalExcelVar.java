package com.example.dztest.apicase.common;


import com.example.dztest.utils.SpringContextUtil;
import com.example.dztest.utils.excel.base.ExcelUtils;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileInputStream;
import java.util.Objects;

/**
 * @author xinxin.wu
 * @description: excel业务变量文件处理
 * @date 2023/10/18
 * @version: 1.0
 */
public class GlobalExcelVar {
    protected final Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * @description: 读取excel业务变量文件，写入全局变量池
     * @param
     * @return void
     */
    public static void setVarFromExcel() {
        try {
            //获取全局变量excel文件
            String path = getExcelPath();

            File file = new File(path);
            FileInputStream fis = new FileInputStream(file);
            Workbook workbook = WorkbookFactory.create(fis);

            for (int i = 0; i < workbook.getNumberOfSheets(); i++) {
                Sheet sheet = workbook.getSheetAt(i);

                // 循环获取每一行数据 因为默认第一行为标题行，我们可以从 1 开始循环，如果需要读取标题行，从 0 开始
                // sheet.getRows() 获取总行数
                for (int j = 0; j < sheet.getPhysicalNumberOfRows(); j++) {
                    Row row = sheet.getRow(j);
                    // 获取第一列的第 j 行信息 sheet.getCell(列，行)，下标从0开始
                    String varName = ExcelUtils.getCellValue(row.getCell(0)).trim();

                    // 获取第二列的第 j 行信息
                    String varValue = ExcelUtils.getCellValue(row.getCell(1)).trim();

                    // 存入本地或者是存入对象等根据给人需求自己定就行,创建对象存储，然后加入集合中
                    GlobalApiVar.globalVars.put("g." + sheet.getSheetName() + "." + varName, varValue);
                }
            }
            System.out.println(GlobalApiVar.globalVars.get("g.speech.empno"));
            workbook.close();
            fis.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * @description: 根据执行环境获取全局变量excel文件
     * @param
     * @return String
     */
    public static String getExcelPath() {
        String active = SpringContextUtil.getActiveProfile();

        System.out.println("In BaseInfo.getExcelPath, the active = " + active);

        if (null == active) {
            active = "stest6";
        }

        String base_path = Objects.requireNonNull(GlobalExcelVar.class.getResource("/")).getPath();
        String path = base_path.replaceFirst("test-classes", "classes") + "excel/";

        return path + active + ".xlsx";
    }

}
