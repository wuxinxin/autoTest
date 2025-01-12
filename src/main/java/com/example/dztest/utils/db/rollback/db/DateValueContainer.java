package com.example.dztest.utils.db.rollback.db;

public interface DateValueContainer {

    /**
     * 返回日期
     * @return 返回日期
     */
    DateValue getDate();

    boolean isMidnight();

}
