package com.example.dztest.feishu;

import com.example.dztest.domain.HttpResponse;
import com.example.dztest.utils.AssertUtil;

/**
 * @description: 飞书返回接口成功判断
 * @author xinxin.wu
 * @date 2024/04/09
 * @version: 1.0
 */
public class FeishuUtil {
    /**
     * 飞书接口返回格式统一用返回码0标识成功
     */
    public static boolean isSuccess(HttpResponse httpResponse) {

        return 0 == (int)AssertUtil.getData(httpResponse, "$.code");
    }
}
