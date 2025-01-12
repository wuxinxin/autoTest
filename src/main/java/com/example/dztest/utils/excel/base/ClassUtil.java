package com.example.dztest.utils.excel.base;

import com.example.dztest.utils.excel.domain.DynamicBean;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.*;

/**
 * @ClassName: ClassUtil
 * @description: ClassUtil
 * @author: jian.ma@msxf.com
 * @create: 2022/06/25
 * @update: 2021/06/25
 **/

public class ClassUtil {

    /**
     * @param object    old obj with value
     * @param addMap    property and it's type will be added dynamic
     * @param addValMap property and it's value will be added dynamic
     * @return new object
     * @throws Exception
     */
    public static Object dynamicClass(Object object, HashMap addMap, HashMap addValMap) throws Exception {
        HashMap returnMap = new HashMap();
        HashMap typeMap = new HashMap();
        Class<?> type = object.getClass();
        BeanInfo beanInfo = Introspector.getBeanInfo(type);

        PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
        for (int i = 0; i < propertyDescriptors.length; i++) {
            PropertyDescriptor descriptor = propertyDescriptors[i];
            String propertyName = descriptor.getName();

            if (!propertyName.equals("class")) {
                Method readMethod = descriptor.getReadMethod();
                Object result = readMethod.invoke(object);

                returnMap.put(propertyName, result);
                typeMap.put(propertyName, descriptor.getPropertyType());
            }
        }

        returnMap.putAll(addValMap);
        typeMap.putAll(addMap);

        //convert map to entity obj
        DynamicBean bean = new DynamicBean(typeMap);

        Set keys = typeMap.keySet();

        for (Iterator it = keys.iterator(); it.hasNext(); ) {
            String key = (String) it.next();
            bean.setValue(key, returnMap.get(key));
        }

        Object obj = bean.getObject();

        return obj;
    }

}