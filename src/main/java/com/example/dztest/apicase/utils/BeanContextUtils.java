package com.example.dztest.apicase.utils;


import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * @author xinxin.wu
 * @description: 通过工具类获取需要的的bean
 * @date 2023/10/18
 * @version: 1.0
 */
@Component
public class BeanContextUtils implements ApplicationContextAware {
    /**
     * 上下文对象实例
     */
    private static ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        BeanContextUtils.applicationContext = applicationContext;
    }

    /**
     * @description: 获取applicationContext
     * @param
     * @return ApplicationContext
     */
    public static ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    /**
     * @description: 通过name获取 Bean
     * @param name
     * @return Object
     */
    public static Object getBean(String name) {
        return getApplicationContext().getBean(name);
    }

    /**
     * @description: 通过class获取Bean
     * @param clazz
     * @return T
     */
    public static <T> T getBean(Class<T> clazz) {
        return getApplicationContext().getBean(clazz);
    }

    /**
     * @description: 通过name,以及Clazz返回指定的Bean
     * @param name
     * @param clazz
     * @return T
     */
    public static <T> T getBean(String name, Class<T> clazz) {
        return getApplicationContext().getBean(name, clazz);
    }

}

