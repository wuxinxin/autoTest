package com.example.dztest.utils.excel.plcy;

import com.example.dztest.utils.SpringContextUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import java.io.File;


/**
 * @ClassName: BaseInfo
 * @description: base info of ExcelPlcy needed
 * @author: jian.ma@msxf.com
 * @create: 2022/06/30
 * @update: 2022/06/30
 **/

public abstract class BaseInfo implements Column2Row {
    private static final Logger logger = LoggerFactory.getLogger(BaseInfo.class);

    public String getExcelPath() {
        String path = "";
        String active = SpringContextUtil.getActiveProfile();

        System.out.println("In BaseInfo.getExcelPath, the active = " + active);

        //TODO 优化jenkins执行没指定active问题==>DONE
        if (null == active) {
            active = "stest6";
        }

//        String winPath = System.getProperty("user.dir")
//                + "\\src\\main\\resources\\excel\\";
//        String linuxPath = System.getProperty("user.dir")
//                + "/src/main/resources/excel/";
//        boolean isWin = System.getProperty("os.name").toLowerCase().contains("win");
//
//        if (isWin) {
//            path = winPath;
//        } else {
//            path = linuxPath;
//        }
        path = "excel/";
        String file = path + active + ".xlsx";
        System.out.println("data file = " + file);

//        if (null == file) {
//            throw new NullPointerException("excel file should not be null.");
//        }

        return file;
    }
}
