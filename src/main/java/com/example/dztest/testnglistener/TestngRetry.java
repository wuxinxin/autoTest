package com.example.dztest.testnglistener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;

/**
 * @description: IRetryAnalyzer接口作用：实现此接口可以让失败的用例运行重试机制;需重新retry方法
 * 在Test注解加上如右侧指定用例执行充实机制 @Test(retryAnalyzer= TestngRetry.class)
 * @author xinxin.wu
 * @date 2024/05/11
 * @version: 1.0
 */
public class TestngRetry implements IRetryAnalyzer {
    // 最大重试次数限制
    private int maxRetryCount = 2;
    // 当前正在重试次数
    private int currentRetryCount = 1;

    protected final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public boolean retry(ITestResult result) {
        // retry返回true，执行重试机制，并且一直重试，所以判断需要重试的次数，分别返回
        if (currentRetryCount < maxRetryCount) {
            logger.info("正在运行第【" + currentRetryCount + "】次");
            currentRetryCount++;
            logger.info("即将运行第【" + currentRetryCount + "】次重试");
            // 如果返回为true表示执行重试机制
            return true;
        } else {
            // 如果返回为false表示不执行重试机制
            return false;
        }
    }
}