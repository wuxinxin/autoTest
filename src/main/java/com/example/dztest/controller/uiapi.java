package com.example.dztest.controller;

import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.logging.LoggingPreferences;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;

@RestController
public class uiapi {

    /**
     * 初始化chrome配置
     *
     * @return
     */
    private static ChromeOptions initChromeOptionsbak() {
        ChromeOptions options = new ChromeOptions();
        Map<String, Object> prefs = new HashMap<>();
        prefs.put("profile.default_content_settings.javascript", 2); // 禁止加载js 2就是代表禁止加载的意思
        prefs.put("profile.default_content_settings.images", 2); // 禁止加载css 2就是代表禁止加载的意思
        options.setExperimentalOption("prefs", prefs);
        options.addArguments("--disable-gpu"); // 禁用GPU硬件加速。如果软件渲染器不到位，则GPU进程将无法启动
        options.addArguments("--load-images=false"); // 禁止图片加载
//        options.addArguments("--start-maximized"); // 最大化
        options.addArguments("--window-size=1366,768"); // 设置初始窗口大小。提供格式为“800,600”的字符串。
        options.addArguments("--no-sandbox"); // 禁用沙箱模式-linux运行
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--use-fake-device-for-media-stream"); //使用假设备进行MediaStream替换实际的摄像头和麦克风
        options.addArguments("--use-fake-ui-for-media-stream"); //通过选择媒体流的默认设备（例如WebRTC）来绕过媒体流信息量。与--use-fake-device-for-media-stream一起使用
        options.addArguments("disable-infobars"); // 隐藏提示-Chrome正在受到自动软件的控制
        options.addArguments("--headless"); // 在无头模式下运行，即没有UI或显示服务器依赖性

        options.addArguments("--user-agent=Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/67.0.3396.87 Safari/537.36");
        options.addArguments("--incognito"); // 隐身模式启动
        options.setAcceptInsecureCerts(true);
        options.setCapability("handlesAlerts", true);
        //        打开日志
        LoggingPreferences logPrefs = new LoggingPreferences();
        logPrefs.enable(LogType.PERFORMANCE, Level.ALL);
        logPrefs.enable(LogType.BROWSER, Level.ALL);
        logPrefs.enable(LogType.DRIVER, Level.ALL);
//        options.setExperimentalOption("perfLoggingPrefs", logPrefs);
        options.setCapability(CapabilityType.LOGGING_PREFS, logPrefs);
        return options;
    }

    /**
     * 初始化chrome配置
     *
     * @return
     */
    private static ChromeOptions initChromeOptions() {
        ChromeOptions options = new ChromeOptions();
        Map<String, Object> prefs = new HashMap<>();
        options.addArguments("--disable-gpu"); // 禁用GPU硬件加速。如果软件渲染器不到位，则GPU进程将无法启动
        options.addArguments("--no-sandbox"); // 禁用沙箱模式-linux运行
        options.addArguments("--headless"); // 在无头模式下运行，即没有UI或显示服务器依赖性
        return options;
    }
    /**
     * 测试页 本地驱动
     *
     * @return
     */
    @RequestMapping(value = "/test", method= RequestMethod.GET)
    public String testIndex() {
        System.setProperty("webdriver.chrome.driver", "C:\\Program Files (x86)\\Google\\Chrome\\Application\\chromedriver2.exe");
        WebDriver driver = new ChromeDriver();
        driver.get("https://www.baidu.com/");
        System.out.println("标题："+ driver.getTitle());
        driver.quit();

        return "run";
    }

    @RequestMapping(value = "/test1", method= RequestMethod.GET)
    public String testIndex1() {
//        System.setProperty("webdriver.chrome.driver", "/usr/bin/chromedriver");
//        System.setProperty("webdriver.chrome.driver", "C:\\Program Files (x86)\\Google\\Chrome\\Application\\chromedriver2.exe");

        System.out.println("start....");
        ChromeOptions options = new ChromeOptions();
        System.out.println("start....11");
        options.addArguments("--disable-gpu"); // 禁用GPU硬件加速。如果软件渲染器不到位，则GPU进程将无法启动
        options.addArguments("--headless"); // 在无头模式下运行，即没有UI或显示服务器依赖性
        options.addArguments("--no-sandbox"); // 禁用沙箱模式-linux运行
        options.addArguments("window-size=1024,768");
        System.out.println("start....22");
        WebDriver driver = new ChromeDriver(options);
        System.out.println("start....33");
        driver.get("http://10.193.196.3:4444/grid/console");
        System.out.println("标题："+ driver.getTitle());
        driver.quit();

        return "run";
    }

    @RequestMapping(value = "/tt", method = RequestMethod.GET)
    public void tt(){
//        System.setProperty("webdriver.chrome.driver", "C:\\Program Files (x86)\\Google\\Chrome\\Application\\chromedriver2.exe");
//		System.setProperty("webdriver.chrome.driver", "/usr/bin/chromedriver");
//        System.setProperty("webdriver.chrome.driver", "E:\\IdeaProjects\\icc_auto_test\\chromedriver");
        DesiredCapabilities capabilities = DesiredCapabilities.chrome();
//		LoggingPreferences logPrefs = new LoggingPreferences();
//		logPrefs.enable(LogType.BROWSER, Level.INFO);
//        capabilities.setJavascriptEnabled(true);
//        capabilities.setBrowserName("chrome");
//		capabilities.setPlatform(Platform.LINUX);
//        capabilities.setPlatform(Platform.WINDOWS);
		capabilities.setCapability(ChromeOptions.CAPABILITY, initChromeOptions());
//		capabilities.setCapability(CapabilityType.LOGGING_PREFS, logPrefs);
        URL url = null;
        try {
            url = new URL("http://10.193.198.165:5555/wd/hub");
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        WebDriver wb=new RemoteWebDriver(url, capabilities);


//        wb.get("https://www.baidu.com");
        wb.get("https://stest3https.zkj.test");
        System.out.println("标题："+ wb.getTitle());

        wb.quit();
    }



}
