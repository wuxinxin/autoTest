package com.example.dztest.utils.excel.plcy;

import com.example.dztest.utils.excel.base.ExcelDataHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.IOException;

/**
 * @ClassName: ColumnExec
 * @description: column mode implements class of Excelplcy
 * @author: jian.ma@msxf.com
 * @create: 2022/06/30
 * @update: 2022/06/30
 **/

@Service("column")
public class ColumnExec extends BaseInfo implements ExcelPlcy {
    protected final Logger logger = LoggerFactory.getLogger(ColumnExec.class);

    @Override
    public void exec() {
        logger.info("In " + ReadModeEnum.COLUMN.getMode() + " way ...");
        ExcelDataHelper.getExcelDataByColumnWay(getExcelPath());
    }
}
