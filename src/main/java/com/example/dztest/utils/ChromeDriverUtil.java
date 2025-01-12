package com.example.dztest.utils;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.logging.LoggingPreferences;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;

public class ChromeDriverUtil {
    private static final String DRIVER_PATH = "D:\\Program Files\\chromedriver.exe";
    public static final Logger LOGGER = LoggerFactory.getLogger(ChromeDriverUtil.class);

    /**
     * 初始本地chrome浏览器
     *
     * @return
     */
    public static WebDriver initLocalChromeDriver() {
        System.setProperty("webdriver.chrome.driver", DRIVER_PATH);
        LOGGER.info("DRIVER_PATH: {}", DRIVER_PATH);
        return new ChromeDriver(initChromeOptions(false));
    }


    /**
     * 初始化远程chrome浏览器
     *
     * @return
     */
    public static WebDriver initRemoteChromeDriver(String remoteUrl) {
        DesiredCapabilities capabilities = DesiredCapabilities.chrome();
        capabilities.setCapability(ChromeOptions.CAPABILITY, initChromeOptions(false));
        LOGGER.info("初始化远程浏览器参数......");
        URL url = null;
        try {
            url = new URL(remoteUrl);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        LOGGER.info("初始化远程浏览器URL......");
        return new RemoteWebDriver(url, capabilities);
    }


    /**
     * @param isHeadless
     * @return
     */
    private static ChromeOptions initChromeOptions(boolean isHeadless) {
        ChromeOptions options = new ChromeOptions();
        Map<String, Object> prefs = new HashMap<>();
//        prefs.put("profile.default_content_settings.javascript", 2); // 禁止加载js 2就是代表禁止加载的意思
//        prefs.put("profile.default_content_settings.images", 2); // 禁止加载css 2就是代表禁止加载的意思
        options.setExperimentalOption("prefs", prefs);
        options.addArguments("--disable-gpu"); // 禁用GPU硬件加速。如果软件渲染器不到位，则GPU进程将无法启动
        options.addArguments("--load-images=false"); // 禁止图片加载
        options.addArguments("--start-maximized"); // 最大化
        options.addArguments("--window-size=1366,768"); // 设置初始窗口大小。提供格式为“800,600”的字符串。
        options.addArguments("--no-sandbox"); // 禁用沙箱模式-linux运行
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--use-fake-device-for-media-stream"); //使用假设备进行MediaStream替换实际的摄像头和麦克风
        options.addArguments("--use-fake-ui-for-media-stream"); //通过选择媒体流的默认设备（例如WebRTC）来绕过媒体流信息量。与--use-fake-device-for-media-stream一起使用
        options.addArguments("disable-infobars"); // 隐藏提示-Chrome正在受到自动软件的控制
        options.addArguments("--test-type", "--ignore-certificate-errors");
        if (isHeadless) {
            options.addArguments("--headless"); // 在无头模式下运行，即没有UI或显示服务器依赖性
        }
        options.addArguments("--user-agent=Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/67.0.3396.87 Safari/537.36");
        options.addArguments("--incognito"); // 隐身模式启动
        options.setAcceptInsecureCerts(true);
        options.setCapability("handlesAlerts", true);
        // 打开日志
        LoggingPreferences logPrefs = new LoggingPreferences();
        logPrefs.enable(LogType.PERFORMANCE, Level.ALL);
        logPrefs.enable(LogType.BROWSER, Level.ALL);
        logPrefs.enable(LogType.DRIVER, Level.ALL);
        //options.setExperimentalOption("perfLoggingPrefs", logPrefs);
        options.setCapability(CapabilityType.LOGGING_PREFS, logPrefs);
        return options;
    }

}
