package com.example.dztest.utils;

import com.example.dztest.utils.db.dbassert.GetBeanException;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.aop.support.AopUtils;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 * 项目名称:
 * 类名: SpringContextUtil
 * 描述： 获取bean的工具类，可用于在线程里面获取bean
 * 创建人: awsm
 * 创建时间: Dec 17, 2015 10:46:44 PM
 * 修改人：little evil
 * 修改时间：May 18, 2018 04:01:34 PM
 * 修改备注：添加getActiveProfile方法,获取当前环境
 * 版本：1.1
 */
@Component
public class SpringContextUtil implements ApplicationContextAware {
    private static ApplicationContext context = null;

    /* (non Javadoc)
     * @Title: setApplicationContext
     * @Description: spring获取bean工具类
     * @param applicationContext
     * @throws BeansException
     * @see org.springframework.context.ApplicationContextAware#setApplicationContext(org.springframework.context.ApplicationContext)
     */
    @Override
    public void setApplicationContext(ApplicationContext applicationContext)
            throws BeansException {
        this.context = applicationContext;
    }

    // 传入线程中
    public static <T> T getBean(String beanName) {
        return (T) context.getBean(beanName);
    }

    public static String getBeanName(Class<?> interfaceClass) throws NoSuchMethodException,
            InvocationTargetException, IllegalAccessException {
        // 根据名称获取Bean
        Annotation[] annotations = interfaceClass.getAnnotations();
        if (!ArrayUtils.isEmpty(annotations)) {
            List<Class<? extends Annotation>> serviceList = Arrays.asList(Service.class,
                    Component.class, Controller.class, Repository.class);
            for (Annotation annotation : annotations) {
                if (serviceList.contains(annotation.annotationType())) {
                    Method m = annotation.getClass().getDeclaredMethod("value");
                    return (String) m.invoke(annotation);
                }
            }
        }
        return null;
    }

    private static String lowerFirst(String str) {
        // 把首字母转为小写
        if (Objects.isNull(str) || str.length() == 0) {
            return str;
        }
        return str.replaceFirst(str.substring(0, 1), str.substring(0, 1).toLowerCase());
    }

    public static Object getBean(Class<?> interfaceType) throws NoSuchMethodException,
            IllegalAccessException, InvocationTargetException {
        // 根据class获取bean的实例
        try {
            return context.getBean(interfaceType);
        } catch (Exception e) {
            try {
                // 接口代理，可以配置 <aop:config proxy-target-class="true">
                return context.getBean(lowerFirst(interfaceType.getSimpleName()));
            } catch (Exception e1) {
                try {
                    // 如果为代理类时获取其目标类
                    Class<?> targetClass = AopUtils.getTargetClass(interfaceType);
                    return context.getBean(targetClass);
                } catch (Exception e2) {
                    // 通过接口获取实例，实现类有名称，例如 @Service("xxxService")
                    String beanName = getBeanName(interfaceType);
                    if (beanName != null) {
                        return context.getBean(beanName);
                    } else {
                        Class<?>[] classes = interfaceType.getInterfaces();
                        if (classes.length > 0) {
                            return context.getBean(classes[0]);
                        }
                    }
                }
            }
        }
        throw new GetBeanException("bean not found : " + interfaceType.getName());
    }

    /// 获取当前环境;如果指定如-Dspring.profiles.active=stest3，则获取值stest3,；如果未指定，则默认返回default
    public static String getActiveProfile() {
        String[] env_s = context.getEnvironment().getActiveProfiles();
        if (env_s.length == 0) {
            return "default";
        } else {
            return env_s[0];
        }
//        return context.getEnvironment().getActiveProfiles()[0];
    }

    /**
     * get context
     *
     * @return
     */
    public static ApplicationContext getApplicationContext() {
        return context;
    }
}
