package com.hong.authservice.aop;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

/**
 * @author liang
 * @description
 * @date 2020/7/6 13:31
 */
public class HelloCGLibProxy implements MethodInterceptor {


    public Object getCGLibProxy(Object object) {
        Enhancer enhancer = new Enhancer();
        //设置目标类的字节码文件
        enhancer.setSuperclass(object.getClass());
        //设置回调函数
        enhancer.setCallback(this);
        //创建代理类
        return enhancer.create();
    }

    @Override
    public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
        System.out.println("对目标类进行增强");
        Object object = methodProxy.invokeSuper(o, objects);
        System.out.println("代理类结束执行...");
        return object;
    }
}
