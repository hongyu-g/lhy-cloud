package com.hong.userservice.bean;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;

/**
 * @author liang
 * @description
 * @date 2020/7/24 18:27
 */
public class MyBeanPostProcessor implements BeanPostProcessor {


    public MyBeanPostProcessor() {
        super();
        System.out.println("这是BeanPostProcessor实现类构造器！！");
    }

    /**
     *
     * @param bean 更改的bean对象
     * @param beanName  更改的bean对象名称
     * @return
     * @throws BeansException
     */
    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        System.out.println("【调用BeanPostProcessor接口】调用BeanPostProcessor.postProcessBeforeInitialization()对属性进行更改");
        return bean;
    }


    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        System.out.println("【调用BeanPostProcessor接口】调用BeanPostProcessor.postProcessAfterInitialization()对属性进行更改");
        return bean;
    }
}
