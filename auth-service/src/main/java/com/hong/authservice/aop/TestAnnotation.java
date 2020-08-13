package com.hong.authservice.aop;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author liang
 * @description
 * @date 2020/6/21 17:35
 */
@Retention(RetentionPolicy.RUNTIME)//表示注解在运行时依然存在
@Target(ElementType.METHOD) // 表示注解可以被使用于方法上
public @interface TestAnnotation {

    String paramValue() default "hong"; // 表示我的注解需要一个参数名为"paramValue" 默认值为"hong"
}
