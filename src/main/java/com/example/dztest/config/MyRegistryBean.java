package com.example.dztest.config;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ResourceLoaderAware;
import org.springframework.core.env.Environment;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternUtils;
import org.springframework.core.type.classreading.CachingMetadataReaderFactory;
import org.springframework.core.type.classreading.MetadataReader;
import org.springframework.core.type.classreading.MetadataReaderFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.ClassUtils;

import java.io.IOException;
import java.util.LinkedHashSet;
import java.util.Set;

@Component
public class MyRegistryBean implements ApplicationContextAware, ResourceLoaderAware, BeanDefinitionRegistryPostProcessor {
    private ApplicationContext ctx;
    private static final String DEFAULT_RESOURCE_PATTERN = "**/*.class";
    private static final String CLASSPATH_ALL_URL_PREFIX = "classpath:";
    private final String INTERFACE_PACKAGE="com.example.dztest.service.interfaces";
    private ResourcePatternResolver resourcePatternResolver;
    private MetadataReaderFactory metadataReaderFactory;

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {}

    //此方法在所有Bean定义将要被加载，Bean实例还未创建的时候运行;它优先于postProcessBeanFactory方法执行
//    @Override
//    public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry beanDefinitionRegistry) throws BeansException{
//        Class<?> cls = Hello1.class;
//        BeanDefinitionBuilder builder = BeanDefinitionBuilder.genericBeanDefinition(cls);
//        GenericBeanDefinition definition = (GenericBeanDefinition) builder.getRawBeanDefinition();
//        definition.getPropertyValues().add("interfaceClass", definition.getBeanClassName());
//        definition.setBeanClass(MyProxyFactory.class);
//        definition.setAutowireMode(GenericBeanDefinition.AUTOWIRE_BY_TYPE);
//        //注册bean，一般为类名首字母想小写
//        beanDefinitionRegistry.registerBeanDefinition("hello1", definition);
//    }

    //此方法在所有Bean定义将要被加载，Bean实例还未创建的时候运行;它优先于postProcessBeanFactory方法执行
    @Override
    public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry beanDefinitionRegistry) throws BeansException{
        Set<Class<?>> beanClasses = scannerPackages(INTERFACE_PACKAGE);
        for (Class beanClazz : beanClasses) {
            BeanDefinitionBuilder builder = BeanDefinitionBuilder.genericBeanDefinition(beanClazz);
            //创建一个bean定义类的对象
            GenericBeanDefinition definition = (GenericBeanDefinition) builder.getRawBeanDefinition();
            definition.getPropertyValues().add("interfaceClass", definition.getBeanClassName());
            definition.setBeanClass(MyProxyFactory.class);
            definition.setAutowireMode(GenericBeanDefinition.AUTOWIRE_BY_TYPE);
            //注册bean，一般为类名首字母小写
            beanDefinitionRegistry.registerBeanDefinition(beanClazz.getSimpleName(), definition);
        }
    }

    /**
     * 实现ApplicationContextAware接口的回调方法，设置上下文环境
     *
     * @param ctx spring上下文对象
     * @throws BeansException 抛出spring异常
     */
    @Override
    public void setApplicationContext(ApplicationContext ctx) throws BeansException{
        this.ctx = ctx;
    }

    @Override
    public void setResourceLoader(ResourceLoader resourceLoader) {
        this.resourcePatternResolver = ResourcePatternUtils.getResourcePatternResolver(resourceLoader);
        this.metadataReaderFactory = new CachingMetadataReaderFactory(resourceLoader);
    }

    private Set<Class<?>> scannerPackages(String basePackage) {
        Set<Class<?>> set = new LinkedHashSet<>();
        String packageSearchPath = MyRegistryBean.CLASSPATH_ALL_URL_PREFIX + resolveBasePackage(basePackage) + '/' + DEFAULT_RESOURCE_PATTERN;
        try {
            Resource[] resources = this.resourcePatternResolver.getResources(packageSearchPath);
            for (Resource resource : resources) {
                if (resource.isReadable()) {
                    MetadataReader metadataReader = this.metadataReaderFactory.getMetadataReader(resource);
                    String className = metadataReader.getClassMetadata().getClassName();
                    try {
                        Class<?> clazz = Class.forName(className);
                        set.add(clazz);
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return set;
    }

    protected String resolveBasePackage(String basePackage) {
        return ClassUtils.convertClassNameToResourcePath(this.getEnvironment().resolveRequiredPlaceholders(basePackage));
    }

    private Environment getEnvironment() {
        return ctx.getEnvironment();
    }

}
