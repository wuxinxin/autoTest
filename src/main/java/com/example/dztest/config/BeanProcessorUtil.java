package com.example.dztest.config;

import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.context.annotation.Configuration;

/**
 * @ClassName: BeanProcessorUtil
 * @description: Bean register
 * @author: jian.ma@msxf.com
 * @update: jian.ma@msxf.com
 * @create: 2021/11/22
 **/

@Configuration
public class BeanProcessorUtil implements BeanFactoryPostProcessor {
    public static final Logger logger = LoggerFactory.getLogger(BeanProcessorUtil.class);

    private static ConfigurableListableBeanFactory beanFactory;

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory)
            throws BeansException {
        logger.info("In postProcessBeanFactory,apply beanFactory...");
        BeanProcessorUtil.beanFactory = beanFactory;
    }

    /**
     * register bean to spring
     * @param beanName
     * @param singleton
     */
    public void register(String beanName, Object singleton) {
        logger.info("Registering  bean: {}", beanName);
        beanFactory.registerSingleton(beanName, singleton);
    }

    /**
     * get bean by beanName
     * @param beanName
     * @return
     */
    public WebDriver getBean(String beanName) {
        return (WebDriver) beanFactory.getBean(beanName);
    }
}