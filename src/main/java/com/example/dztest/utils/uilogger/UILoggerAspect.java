package com.example.dztest.utils.uilogger;

import com.example.dztest.service.BaseOfTestCase;
import com.example.dztest.ui.UIDriver;
import com.example.dztest.utils.screen_shot.ScreenShotUtils;
import com.example.dztest.utils.screen_shot.UIScreenShotByExceptionS;
import org.apache.commons.io.FileUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.stereotype.Component;
import com.alibaba.fastjson.JSONObject;

import java.io.File;
import java.lang.reflect.Method;

/**
 * @ClassName: UILoggerAspect
 * @description: Aspect Class
 * @author: jian.ma@msxf.com
 * @create: 2022/03/02
 **/

@Aspect
@Component
@SuppressWarnings(value = "ALL")
public class UILoggerAspect implements U2IAnno {
    private static final Logger logger = LoggerFactory.getLogger(UILoggerAspect.class);
    private WebDriver webDriver;

    @Autowired
    protected UIDriver uiDriver;

    @Value("${screen.all}")
    private String screenMode;

    @Pointcut("@annotation(com.example.dztest.utils.uilogger.UILogger)")
    private void commonPointCut() {

    }

    @Around("commonPointCut()")
    public Object around(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        MethodSignature signature = (MethodSignature) proceedingJoinPoint.getSignature();
        Method method = signature.getMethod();
        UILogger uiLogger = method.getAnnotation(UILogger.class);

        String methodName = proceedingJoinPoint.getSignature().getName();
        Class<?> targetClass = proceedingJoinPoint.getTarget().getClass();
        Object[] params = proceedingJoinPoint.getArgs();
        String paramesVInfo = "=>入参:";

        for (Object param : params) {
            paramesVInfo += param + "/";
        }
        paramesVInfo = replaceLast(paramesVInfo, "/", "");

        String stempInfo = "调用" + targetClass + "的方法" + methodName + "=>" + uiLogger.desc() + paramesVInfo;

        if (uiLogger.isPage() && "true".equals(screenMode)) {
            webDriver = uiDriver.getWebDriver();
            String fileName = BaseOfTestCase.getStaticPath() + BaseOfTestCase.getDateTime() + ".png";

            ScreenShotUtils.takeScreenshot(webDriver, (screeShot) -> {
                logger.info("driver is present !!!" + webDriver);
                File srcFile = ((TakesScreenshot) webDriver).getScreenshotAs(OutputType.FILE);
                byte[] screenshotAs = ((TakesScreenshot) webDriver).getScreenshotAs(OutputType.BYTES);
                try {
                    FileUtils.copyFile(srcFile, new File(fileName));
                    BaseOfTestCase.takePhotoToAllureReport("[步骤" + BaseOfTestCase.stepCount + "]" + stempInfo, screenshotAs);
                } catch (Exception ex) {
                    logger.info("FileUtils.copyFile Exception: {}", ex);
                }
                return null;
            });
        } else {
            BaseOfTestCase.logStep(stempInfo);
        }
        //置后执行target，否则执行target方法是出现异常就不会执行日志切面
        Object proceed = proceedingJoinPoint.proceed();
        return proceed;
    }

    private UILogger getAnnotationInfo(ProceedingJoinPoint joinPoint) throws NoSuchMethodException {
        String methodName = joinPoint.getSignature().getName();
        Class<?> targetClass = joinPoint.getTarget().getClass();
        Class<?>[] parameterTypes = ((MethodSignature) joinPoint.getSignature()).getParameterTypes();
        Method method = targetClass.getMethod(methodName, parameterTypes);

        UILogger annotation = method.getDeclaredAnnotation(UILogger.class);
        return annotation;
    }

    /**
     * 替换第一个字符
     *
     * @param ret
     * @param strToReplace
     * @param replaceWithThis
     * @return
     */
    private static String replaceFirst(String ret, String strToReplace, String replaceWithThis) {
        return ret.replaceFirst(strToReplace, replaceWithThis);
    }

    /**
     * 替换最后一个字符
     *
     * @param text
     * @param strToReplace
     * @param replaceWithThis
     * @return
     */
    public static String replaceLast(String text, String strToReplace, String replaceWithThis) {
        return text.replaceFirst("(?s)" + strToReplace + "(?!.*?" + strToReplace + ")", replaceWithThis);
    }
}