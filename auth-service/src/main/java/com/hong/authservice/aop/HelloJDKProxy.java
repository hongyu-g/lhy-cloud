package com.hong.authservice.aop;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * @author liang
 * @description
 * @date 2020/7/6 11:45
 */
public class HelloJDKProxy implements InvocationHandler {

    private Object object;

    public HelloJDKProxy(Object object) {
        this.object = object;
    }

    public Object getJDKProxy() {
        return Proxy.newProxyInstance(object.getClass().getClassLoader(), object.getClass().getInterfaces(), this);
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println("代理类开始执行...");
        method.invoke(object, args);
        System.out.println("代理类结束执行...");
        return proxy;
    }

}
