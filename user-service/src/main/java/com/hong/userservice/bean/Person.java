package com.hong.userservice.bean;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.*;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * @author liang
 * @description
 * @date 2020/7/1 16:50
 */
public class Person implements BeanFactoryAware, BeanNameAware, InitializingBean, DisposableBean,
        ApplicationContextAware {

    private int id;
    private String name;

    private BeanFactory beanFactory;

    private String beanName;

    private ApplicationContext applicationContext;


    public Person() {
        System.out.println("【构造器】调用Person的构造器实例化");
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        System.out.println("【注入属性】注入属性id");
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        System.out.println("【注入属性】注入属性name");
        this.name = name;
    }

    public BeanFactory getBeanFactory() {
        return beanFactory;
    }

    public String getBeanName() {
        return beanName;
    }

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        System.out.println("【BeanFactoryAware接口】调用BeanFactoryAware.setBeanFactory()");
        this.beanFactory = beanFactory;
    }

    @Override
    public void setBeanName(String name) {
        System.out.println("【BeanNameAware接口】调用BeanNameAware.setBeanName()");
        this.beanName = name;
    }

    @Override
    public void destroy() throws Exception {
        System.out.println("【DisposableBean接口】调用DisposableBean.destroy()");
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println("【InitializingBean接口】调用InitializingBean.afterPropertiesSet()");
    }

    public void myInit() {
        System.out.println("【init-method】调用<bean>的init-method属性指定的初始化方法");
    }

    public void myDestroy() {
        System.out.println("【destroy-method】调用<bean>的destroy-method属性指定的初始化方法");
    }


    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        System.out.println("【ApplicationContextAware接口】调用ApplicationContextAware.setApplicationContext()");
        this.applicationContext = applicationContext;
    }
}
