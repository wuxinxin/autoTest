package com.example.dztest.ui;

import org.openqa.selenium.ElementNotInteractableException;
import org.openqa.selenium.WebElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UIWebElement implements WebEle {
    private final WebElement el;
    public final Logger LOGGER = LoggerFactory.getLogger(this.getClass());


    public UIWebElement(WebElement el) {
        this.el = el;
    }

    public WebElement getEl() {
        return this.el;
    }

    public void click() {
        //循环点击，直到元素状态变为可点击
        long start = System.currentTimeMillis();
        for (int i = 0; i < 20; i++) {
            try {
                Thread.sleep(500);
                this.el.click();
                break;
            } catch (ElementNotInteractableException e) {
                long tm = System.currentTimeMillis() - start;
                if (i == 19 || tm > 20000) {
                    throw e;
                } else {
                    LOGGER.warn("ElementNotInteractableException：-" + i, e);
                }
            } catch (InterruptedException e) {
                LOGGER.error("", e);
            }
        }
    }

    public void sendKeys(String key) {
        //循环输入，直到元素状态变为有效
        long start = System.currentTimeMillis();
        for (int i = 0; i < 20; i++) {
            try {
                Thread.sleep(500);
                this.el.sendKeys(key);
                break;
            } catch (ElementNotInteractableException e) {
                long tm = System.currentTimeMillis() - start;
                if (i == 19 || tm > 20000) {
                    throw e;
                } else {
                    LOGGER.warn("ElementNotInteractableException：-" + i, e);
                }
            } catch (InterruptedException e) {
                LOGGER.error("", e);
            }
        }
    }

    public String getText() {
        return this.el.getText();
    }

    public String getTagName() {
        return this.el.getTagName();
    }

    public String getCssValue(String s) {
        return this.el.getCssValue(s);
    }

    public String getAttribute(String s) {
        return this.el.getAttribute(s);
    }

    public boolean isDisplayed() {
        return this.el.isDisplayed();
    }

    public void clear() {
        this.el.clear();
    }

    public boolean isEnabled() {
        return this.el.isEnabled();
    }

    public boolean isSelected() {
        return this.el.isSelected();
    }

    public void submit() {
        this.el.submit();
    }
}
