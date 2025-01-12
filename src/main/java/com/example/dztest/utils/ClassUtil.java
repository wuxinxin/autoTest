package com.example.dztest.utils;

import com.example.dztest.service.interfaces.kms.robot_entity.listener.EasyExcelListener;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.core.type.classreading.CachingMetadataReaderFactory;
import org.springframework.core.type.classreading.MetadataReader;
import org.springframework.core.type.classreading.MetadataReaderFactory;
import org.springframework.util.ClassUtils;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class ClassUtil {

    private final static String BASE_PACKAGE = "com.example.dztest.service.interfaces.kms.robot_entity.listener";
    private final static String RESOURCE_PATTERN = "/**/*.class";

    /**
     * 获取dir下的所有类
     * @return
     */
    public static Map getAllClassOfDir() {
        Map<String, Class> handlerMap = new HashMap<String, Class>();

        ResourcePatternResolver resourcePatternResolver = new PathMatchingResourcePatternResolver();
        try {
            String pattern = ResourcePatternResolver.CLASSPATH_ALL_URL_PREFIX +
                    ClassUtils.convertClassNameToResourcePath(BASE_PACKAGE) + RESOURCE_PATTERN;
            Resource[] resources = resourcePatternResolver.getResources(pattern);

            MetadataReaderFactory readerFactory = new CachingMetadataReaderFactory(resourcePatternResolver);
            for (Resource resource : resources) {
                MetadataReader reader = readerFactory.getMetadataReader(resource);
                String classname = reader.getClassMetadata().getClassName();
                Class<?> clazz = Class.forName(classname);

                EasyExcelListener easyExcelListener = clazz.getAnnotation(EasyExcelListener.class);
                if (easyExcelListener != null) {
                    handlerMap.put(classname, clazz);
                }
            }
        } catch (IOException | ClassNotFoundException e) {
        }
        return handlerMap;
    }
}
