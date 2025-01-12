package com.example.dztest.ui;

import com.example.dztest.config.BeanProcessorUtil;
import com.example.dztest.utils.SpringContextUtil;
import com.example.dztest.utils.uilogger.UILogger;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.function.Predicate;
import java.util.stream.Stream;

@Component
public class UIDriver implements WebDriverUtil {
    public static final Logger logger = LoggerFactory.getLogger(UIDriver.class);

    private WebDriver driver;
    private Object isRemote;
    private List<WebDriver> webDriverList = new ArrayList<WebDriver>();

    @Value("${ui.mode}")
    private String mode;

    @Value("${static.path}")
    private String staticPath;

    @Autowired
    ChromeDriverInit chromeDriverInit;

    @Autowired
    BeanProcessorUtil springUtil;


    /*
     * 截图,返回字节数组
     */
    public byte[] takeScreenShot() {
        byte[] screenshotAs = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
        return screenshotAs;
    }

    public static boolean isBeanExists(Object o, Predicate<Object> predicate) {
        return predicate.test(o);
    }

    public String getStaticPath() {
        if ((boolean) this.getIsRemote()) {
            return this.staticPath;
        } else {
            String base_path = getClass().getResource("/").getPath().replaceFirst("/", "");
            String staticPath = base_path.replaceFirst("test-classes", "classes") + "static";
            return staticPath;
        }
    }

    private Stream<WebDriver> getAllDriver() {
        Map<String, WebDriver> map = SpringContextUtil.getApplicationContext()
                .getBeansOfType(WebDriver.class);

        Stream<WebDriver> stream = map.entrySet().stream().map(
                v -> v.getValue()
        );

        return stream;
    }

    public Object getIsRemote() {
        return this.isRemote;
    }

    //切换到指定驱动
    public void switchTo(int num) {
        this.driver = webDriverList.get(num - 1);
    }

    //切换浏览器标签页
    public void switch_to_window_handles(){
        String mainHandle = driver.getWindowHandle();
        Set<String> Handles = driver.getWindowHandles();
        for (String hand : Handles) {
            if (!hand.equals(mainHandle)) {
                driver.switchTo().window(hand);
                break;
            }
        }
    }
    //关闭当前驱动
    public void quit() {
        this.driver.quit();
    }

    //关闭所有驱动
    @UILogger(desc = "关闭所有驱动")
    public void quitAll() {
        for (int i = 0; i < this.webDriverList.size(); i++) {
            logger.info("quit driver " + webDriverList.get(i));
            this.webDriverList.get(i).close();
            this.webDriverList.get(i).quit();
        }
        this.webDriverList.clear();
    }


    public void switchDriverTo(Object o) {
        if (o instanceof String) {
            this.driver = getDriverByName(o.toString());
            return;
        }

        if (o instanceof WebDriver) {
            this.driver = (WebDriver) o;
            return;
        }

        try {
            throw new Exception("Parameter exception !!!");
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    public void switchDriverByDriverName(String name) {
        this.driver = getDriverByName(name);
    }

    public WebDriver createNewDriver(@NotNull String name) {
        logger.info("Create a new web driver now,named: {}", name);
        if ("chrome_remote".equals(mode)) {
            logger.info("mode:chrome_remote");
            this.driver = chromeDriverInit.initRemoteChromeDriver();
        } else {
            logger.info("" + chromeDriverInit);
            this.driver = chromeDriverInit.initLocalChromeDriver();
        }
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

        this.addDriverToSpringBean(name, this.driver);
        return this.driver;
    }

    private void addDriverToSpringBean(@NotNull String name, WebDriver webDriver) {
        springUtil.register(name, webDriver);
    }

    public WebDriver getDriverByName(String beanName) {
        WebDriver bean = springUtil.getBean(beanName);
        return bean;
    }

    public void init() {
        if ("chrome_remote".equals(mode)) {
            logger.info("mode:chrome_remote");
            this.isRemote = true;
            this.driver = chromeDriverInit.initRemoteChromeDriver();
        } else {
            logger.info("" + chromeDriverInit);
            this.isRemote = false;
            this.driver = chromeDriverInit.initLocalChromeDriver();
        }

        this.webDriverList.add(this.driver);
        this.driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
    }

    public void get(String url) {
        this.driver.get(url);
    }

    public void open(String url) {
        this.init();
        this.driver.get(url);
    }

    public void close() {
        this.getAllDriver().forEach(x -> {
            logger.info("quit driver : {}" + x);
            x.quit();
        });
    }

    public WebElement xpathClick(String xpath) {
        WebDriverWait webDriverWait = new WebDriverWait(this.driver, 10);
        WebElement webElement = webDriverWait.until(ExpectedConditions.elementToBeClickable(By.xpath(xpath)));
        return webElement;
    }

    public WebDriver getWebDriver() {
        return driver;
    }

    public void switch_to_default_content() {
        driver.switchTo().defaultContent();
    }

    public void switchToFrame(String iframeNameOrId) {
        this.driver.switchTo().frame(iframeNameOrId);
    }

    public void switchToFrame(int index) {
        this.driver.switchTo().frame(index);
    }

    public void switchToByXpath(String xpath) {
        this.driver.switchTo().frame(this.xpathUI(xpath).getEl());
    }

    public UIDriver sleep(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return this;
    }

    public Object execJs(String js, Object... objects) {
        JavascriptExecutor executor = (JavascriptExecutor) driver;
        return executor.executeScript(js, objects);
    }

    public Object execJsClick(UIWebElement el) {
        return this.execJs("arguments[0].click();", el.getEl());
    }

    public Object execJsSendKeys(UIWebElement el, String text) {
        return this.execJs("arguments[0].value=" + text, el.getEl());
    }

    public UIWebElement css(String selector) {
        return new UIWebElement(driver.findElement(By.cssSelector(selector)));
    }

    public WebElement xpath(String xpath) {
        return driver.findElement(By.xpath(xpath));
    }

    public UIWebElement xpathUI(String xpath) {
//        sleep(500);
        return new UIWebElement(driver.findElement(By.xpath(xpath)));
    }

    //进入iframe时，页面可能存在多次刷新，导致页面元素失效,抛出StaleElementReferenceException
    //该方法 点击多次，探测是否存在刷新，有异常证明刷新成功，后续可以放心使用；没有异常探测次数耗尽，最终结果就是增加等待时间
    //该方法慎用，不存在页面多次刷新的地方，不能用，会导致增加等待时间
    //从selenium 2.16开始 增加了StaleElementReferenceException异常。在页面元素进行刷新后，再次对此元素进行操作则会抛出这个异常
    public UIWebElement testPageLoad(String xpath) {
        for (int i = 0; i < 5; i++) {
            try {
                this.sleep(1000).xpathUI(xpath).isEnabled();
            } catch (StaleElementReferenceException e) { //出现异常已完成刷新
                logger.warn("testPageLoad:\n" + e.getMessage());
                break;
            }
        }
        return xpathUI(xpath);
    }

    //通过xpath查询元素是否存在
    public boolean isExist(String xpath) {
        try {
            driver.findElement(By.xpath(xpath));
            return true;
        } catch (Exception e) {
            logger.error("不存在此元素 {}", xpath);
            return false;
        }
    }

    //等于节点文本
    public UIWebElement findByText(String nodeName, String text) {
        return xpathUI("//" + nodeName + "[text()='" + text + "']");
    }

    //匹配包含文本
    public UIWebElement findByContainsText(String nodeName, String text) {
        return xpathUI("//" + nodeName + "[contains(text(),'" + text + "')]");
    }

    //input提示文本匹配
    public UIWebElement findByPlaceholder(String Placeholder) {
        return xpathUI("//input[@placeholder='" + Placeholder + "']");
    }

    //css class前缀匹配
    public UIWebElement findByCssPre(String classPre) {
        return css("[class^='" + classPre + "']");
    }

    public UIWebElement findBySpanText(String text) {
        return findByText("span", text);
    }

    public UIWebElement findByLiText(String text) {
        return findByText("li", text);
    }

    public UIWebElement findByDivText(String text) {
        return findByText("div", text);
    }

    public UIWebElement findBySpanContainsText(String text) {
        return findByContainsText("span", text);
    }

    public UIWebElement findByLiContainsText(String text) {
        return findByContainsText("li", text);
    }

    public UIWebElement findByDivContainsText(String text) {
        return findByContainsText("div", text);
    }
}
