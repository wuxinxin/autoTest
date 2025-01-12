package com.example.dztest.utils.screen_shot;

import com.example.dztest.utils.uilogger.U2IAnno;
import org.apache.commons.io.FileUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.joda.time.DateTime;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.logging.LoggingPreferences;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;

/**
 * @ClassName: ScreenShotAspect
 * @description: Aspect Class
 * @author: jian.ma@msxf.com
 * @create: 2021/11/9
 **/

@Aspect
@Component
@SuppressWarnings(value = "ALL")
public class ScreenShotAspect implements U2IAnno {
    private static final Logger logger = LoggerFactory.getLogger(ScreenShotAspect.class);

    //@Pointcut("execution(* com.example.dztest..*(..))")
    @Pointcut("@annotation(com.example.dztest.utils.screen_shot.UIScreenShotByExceptionS)")
    private void commonPointCut() {

    }

    @Around("commonPointCut()")
    public void around(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        String methodName = proceedingJoinPoint.getSignature().getName();
        Object[] params = proceedingJoinPoint.getArgs();
        String fileName = methodName + this.getDateTime() + ".png";

        UIScreenShotByExceptionS uiScreenShotByException = getAnnotationInfo(proceedingJoinPoint);
        logger.info("Annotation info: {}", uiScreenShotByException);

        WebDriver webDriver = (WebDriver) params[0];
        logger.info("In around,the webDriver[(WebDriver) params[0]] is {}", webDriver);

        try {
            logger.info("Start proceeding the original method like dynamic proxy");
            Object result = proceedingJoinPoint.proceed();
            logger.info("The result of proceed {}", result);
        } catch (Throwable e) {
            logger.info("Error happend in method {}, Screen shot now", methodName);
            //todo:　judge the exception types and isLogger for more actions

            ScreenShotUtils.takeScreenshot(webDriver, (screeShot) -> {
                logger.info("In ScreenShotAspect catch,the webDriver is {}", webDriver);

                File srcFile = ((TakesScreenshot) webDriver).getScreenshotAs(OutputType.FILE);
                try {
                    FileUtils.copyFile(srcFile, new File("D:\\" + fileName));
                } catch (Exception ex) {
                    logger.info("FileUtils.copyFile Exception: {}", ex);
                }
                return null;
            });
        } finally {
            logger.info("Start closing webdriver:{}", webDriver);
            webDriver.quit();
        }
    }

    /**
     * Inner way
     *
     * @param joinPoint
     * @return
     * @throws NoSuchMethodException
     */
    private UIScreenShotByExceptionS getAnnotationInfo(ProceedingJoinPoint joinPoint) throws NoSuchMethodException {
        String methodName = joinPoint.getSignature().getName();
        Class<?> targetClass = joinPoint.getTarget().getClass();
        Class<?>[] parameterTypes = ((MethodSignature) joinPoint.getSignature()).getParameterTypes();
        Method method = targetClass.getMethod(methodName, parameterTypes);

        UIScreenShotByExceptionS annotation = method.getDeclaredAnnotation(UIScreenShotByExceptionS.class);
        return annotation;
    }

    private String getDateTime() {
        DateTime currentDateTime = new DateTime();
        return currentDateTime.toString("YYYY-MM-dd-hh-mm-ss");
    }

}