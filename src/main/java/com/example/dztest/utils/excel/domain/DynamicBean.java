package com.example.dztest.utils.excel.domain;

import net.sf.cglib.beans.BeanGenerator;
import net.sf.cglib.beans.BeanMap;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * @ClassName: DynamicBean
 * @description: DynamicBean container of data from excel
 * @author: jian.ma@msxf.com
 * @create: 2022/06/25
 * @update: 2021/06/25
 **/

public class DynamicBean {

    //class generate dynamic
    private Object object = null;

    //save property and it's type
    private BeanMap beanMap = null;

    public DynamicBean() {
        super();
    }

    public DynamicBean(Map propertyMap) {
        this.object = generateBean(propertyMap);
        this.beanMap = BeanMap.create(this.object);
    }

    /**
     *
     * @param propertyMap
     * @return
     */
    private Object generateBean(Map propertyMap) {
        BeanGenerator generator = new BeanGenerator();
        Set keySet = propertyMap.keySet();
        for (Iterator i = keySet.iterator(); i.hasNext(); ) {
            String key = (String) i.next();
            generator.addProperty(key, (Class) propertyMap.get(key));
        }
        return generator.create();
    }

    /**
     * set value to bean
     * @param property
     * @param value
     */
    public void setValue(Object property, Object value) {
        beanMap.put(property, value);
    }

    /**
     * get property value by property name
     * @param property
     * @return
     */
    public Object getValue(String property) {
        return beanMap.get(property);
    }

    /**
     * get bean obj
     * @return
     */
    public Object getObject() {
        return this.object;
    }
}