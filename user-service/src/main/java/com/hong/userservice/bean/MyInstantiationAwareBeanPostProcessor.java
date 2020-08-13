package com.hong.userservice.bean;

import org.springframework.beans.BeansException;
import org.springframework.beans.PropertyValues;
import org.springframework.beans.factory.config.InstantiationAwareBeanPostProcessorAdapter;

/**
 * @author liang
 * @description
 * @date 2020/7/24 18:32
 */
public class MyInstantiationAwareBeanPostProcessor extends InstantiationAwareBeanPostProcessorAdapter {

    public MyInstantiationAwareBeanPostProcessor() {
        super();
        System.out.println("这是InstantiationAwareBeanPostProcessorAdapter实现类构造器！！");
    }

    @Override
    public Object postProcessBeforeInstantiation(Class<?> beanClass, String beanName) throws BeansException {
        System.out.println("调用InstantiationAwareBeanPostProcessorAdapter.postProcessBeforeInstantiation(), 实例化Bean之前调用");
        return null;
    }


    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        System.out.println("调用InstantiationAwareBeanPostProcessorAdapter.postProcessAfterInitialization(),实例化Bean之后调用");
        return bean;
    }

    @Override
    public PropertyValues postProcessProperties(PropertyValues pvs, Object bean, String beanName)
            throws BeansException {
        System.out.println("调用InstantiationAwareBeanPostProcessorAdapter.postProcessProperties()，设置某个属性时调用");
        return pvs;
    }
}
