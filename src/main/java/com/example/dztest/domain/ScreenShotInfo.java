package com.example.dztest.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @ClassName: ScreenShotInfo
 * @description: ScreenShotInfo
 * @author: jian.ma@msxf.com
 * @create: 2021/11/11
 **/

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ScreenShotInfo<T> {
    private String path;
    private boolean result;
    private T data;

    public ScreenShotInfo(String path, boolean result) {
        this(path, result, null);
    }
}
