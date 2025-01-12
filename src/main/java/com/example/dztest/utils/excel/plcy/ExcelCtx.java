package com.example.dztest.utils.excel.plcy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName: ExcelCtx
 * @description: context of implements class
 * @author: jian.ma@msxf.com
 * @create: 2022/06/30
 * @update: 2022/06/30
 **/

@Service
public class ExcelCtx {
    @Autowired
    private static Map<String, ExcelPlcy> map;
}
