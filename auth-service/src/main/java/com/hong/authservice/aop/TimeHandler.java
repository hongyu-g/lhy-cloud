package com.hong.authservice.aop;

import lombok.extern.slf4j.Slf4j;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.aop.AfterReturningAdvice;
import org.springframework.aop.MethodBeforeAdvice;
import org.springframework.aop.ThrowsAdvice;

import java.lang.reflect.Method;

/**
 * @author liang
 * @description
 * @date 2020/7/3 18:01
 */
@Slf4j
public class TimeHandler {


    public static class BeforeAdviceTest implements MethodBeforeAdvice {
        @Override
        public void before(Method method, Object[] objects, Object o) throws Throwable {
            log.info("start time:{}", System.currentTimeMillis());
        }

    }


    public static class AfterReturningAdviceTest implements AfterReturningAdvice {
        @Override
        public void afterReturning(Object returnValue, Method method, Object[] args, Object target) throws Throwable {
            log.info("end time:{}", System.currentTimeMillis());
        }
    }


    public static class ThrowsAdviceTest implements ThrowsAdvice {
        public void afterThrowing(Exception ex) {
            log.error("发生异常", ex);
        }
    }


    public static class AdviceTest2 implements MethodInterceptor {
        @Override
        public Object invoke(MethodInvocation invocation) throws Throwable {
            log.info("start time:{}", System.currentTimeMillis());
            try {
                Object result = invocation.proceed();
                log.info("end time:{}", System.currentTimeMillis());
                return "world";
            } catch (Exception e) {
                System.out.println("异常抛出增强执行");
                return "error";
            }
        }
    }

}
