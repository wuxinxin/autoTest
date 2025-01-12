package com.example.dztest.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.dztest.common.annotations.*;
import com.example.dztest.domain.HttpResponse;
import com.example.dztest.domain.HttpType;
import com.example.dztest.domain.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.InputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.HashMap;
import java.util.Map;

@Component
public class ProxyUtils {
    public static String server_url;

    public static String A_SERVER_URL;

    @Value("${a.gateway.url}")
    public void setaServerUrl(String serverUrl) {
        ProxyUtils.A_SERVER_URL = serverUrl;
    }

    @Value("${gateway.url}")
    public void set_Serverurl(String server_url) {
        ProxyUtils.server_url = server_url;
    }

    public static <T> T create(Class<T> cla) {
        return (T) Proxy.newProxyInstance(cla.getClassLoader(), new Class[]{cla}, new MyInvocationHandler());
    }

    //每一个动态代理类的调用处理程序都必须实现InvocationHandler接口
    public static class MyInvocationHandler implements InvocationHandler {

        /**
         * proxy:代理类代理的真实代理对象com.sun.proxy.$Proxy0
         * method:我们所要调用某个对象真实的方法的Method对象
         * args:指代代理对象方法传递的参数
         */
        @Override
        public HttpResponse invoke(Object proxy, Method method, Object[] args) throws Throwable {
            MethodRequest methodRequest = new MethodRequest(method, args);
            if ("/admin".equals(methodRequest.getZuul_path())) {
                methodRequest.getHttpRequest().setServer_url(A_SERVER_URL);
            } else {
                methodRequest.getHttpRequest().setServer_url(server_url);
            }

            HttpResponse ob = HttpUtil.getResponse(methodRequest.getMethod_name(), methodRequest.getHttpRequest());
            System.out.println("ob = " + ob);
            return ob;
        }
    }

}
