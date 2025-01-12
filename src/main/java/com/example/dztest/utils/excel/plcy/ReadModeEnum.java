package com.example.dztest.utils.excel.plcy;

/**
 * @ClassName: ReadModeEnum
 * @description: the mode list of read excel
 * @author: jian.ma@msxf.com
 * @create: 2022/07/01
 * @update: 2022/07/01
 **/
public enum ReadModeEnum implements Column2Row {
    ROW("row"),
    COLUMN("column");

    private String mode;

    ReadModeEnum(String mode) {
        this.mode = mode;
    }

    public String getMode() {
        return mode;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

}
