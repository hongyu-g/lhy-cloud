package com.hong.common.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author liang
 * @description
 * @date 2020/7/8 15:49
 */
@Retention(RetentionPolicy.RUNTIME)//表示注解在运行时依然存在
@Target(ElementType.METHOD) // 表示注解可以被使用于方法上
public @interface CheckToken {
    boolean required() default true;
}
