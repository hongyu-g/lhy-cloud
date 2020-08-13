package com.hong.common.annotation;

import com.hong.common.enums.AccessLimitType;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author liang
 * @description
 * @date 2020/7/20 14:17
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface AccessLimit {

    int seconds();

    int maxCount();

    boolean check() default true;

    AccessLimitType limitType() default AccessLimitType.USER;
}
