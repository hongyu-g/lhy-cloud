package com.hong.authservice.aop;


import org.springframework.aop.ClassFilter;
import org.springframework.aop.MethodMatcher;
import org.springframework.aop.Pointcut;
import org.springframework.aop.support.StaticMethodMatcherPointcut;

import java.lang.reflect.Method;

public class DemoPointCut implements Pointcut {

    /**
     * 在类级别进行过滤
     * @return
     */
    @Override
    public ClassFilter getClassFilter() {
        return ClassFilter.TRUE;
    }

    /**
     * 在方法级别进行过滤
     * @return
     */
    @Override
    public MethodMatcher getMethodMatcher() {
        return new StaticMethodMatcherPointcut() {
            @Override
            public boolean matches(Method method, Class<?> aClass) {
                return method.getName().equals("hello");
            }
        };
    }
}
