package com.example.dztest.config;

import com.example.dztest.utils.ProxyUtils;
import org.springframework.beans.factory.FactoryBean;

//代理工厂
//FactoryBean是一个工厂Bean，可以生成某一个类型Bean实例，它最大的一个作用是：可以让我们自定义Bean的创建过程
public class MyProxyFactory<T> implements FactoryBean {
    private  Class<T> interfaceClass;
    public Class<T> getInterfaceClass(){
        return interfaceClass;
    }
    public void setInterfaceClass(Class<T> interfaceClass){
        this.interfaceClass = interfaceClass;
    }

    //返回对象的实例
    @Override
    public T getObject() throws Exception{
        return (T) ProxyUtils.create(interfaceClass);
    }

    //bean的类型
    @Override
    public Class<?> getObjectType(){
        return interfaceClass;
    }

    //true是单例，false是非单例；默认是单例
    @Override
    public boolean isSingleton(){
        //单例模式
        return true;
    }
}
