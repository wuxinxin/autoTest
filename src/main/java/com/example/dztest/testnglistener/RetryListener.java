package com.example.dztest.testnglistener;

import org.testng.IAnnotationTransformer;
import org.testng.IRetryAnalyzer;
import org.testng.annotations.ITestAnnotation;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

/**
 * @description: IAnnotationTransformer接口作用：在TestNG执行过程中动态修改@Test注解的参数
 * @author xinxin.wu
 * @date 2024/05/13
 * @version: 1.0
 */
public class RetryListener implements IAnnotationTransformer {
    @Override
    public void transform(ITestAnnotation annotation, Class testClass, Constructor
            testConstructor, Method testMethod) {
        IRetryAnalyzer iRetryAnalyzer = annotation.getRetryAnalyzer();
        if (iRetryAnalyzer == null) {
            annotation.setRetryAnalyzer(TestngRetry.class);
        }
    }
}
