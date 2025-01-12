package com.example.dztest.utils.excel.plcy;

import com.example.dztest.utils.excel.base.ExcelDataHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * @ClassName: RowExec
 * @description: row mode implements class of Excelplcy
 * @author: jian.ma@msxf.com
 * @create: 2022/06/30
 * @update: 2022/06/30
 **/
@Service("row")
public class RowExec extends BaseInfo implements ExcelPlcy {
    protected final Logger logger = LoggerFactory.getLogger(RowExec.class);

    private static void getExcelData(String file) {
        ExcelDataHelper.getExcelData(file);
    }

    @Override
    public void exec() {
        logger.info("In " + ReadModeEnum.ROW.getMode() + " way ...");
        getExcelData(super.getExcelPath());
    }
}
