package com.hong.authservice.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

/**
 * @author liang
 * @description
 * @date 2020/7/3 18:01
 */
@Slf4j
//@Component
@Aspect
public class AdviceHandler {


    /**
     * 切面方法说明：
     *
     * @Aspect -- 作用是把当前类标识为一个切面供容器读取
     * @Pointcut -- (切入点):就是带有通知的连接点，在程序中主要体现为书写切入点表达式
     * @Before -- 标识一个前置增强方法，相当于BeforeAdvice的功能
     * @AfterReturning -- 后置增强，相当于AfterReturningAdvice，方法退出时执行
     * @AfterThrowing -- 异常抛出增强，相当于ThrowsAdvice
     * @After -- final增强，不管是抛出异常或者正常退出都会执行
     * @Around -- 环绕增强，相当于MethodInterceptor
     */

    @Pointcut("execution(* com.hong.authservice.service..*.*(..))")
    public void pointcut() {

    }


    @Before("pointcut()")
    public void before(JoinPoint joinPoint) {
        log.info("start time:{}", System.currentTimeMillis());
    }

    @After("pointcut()")
    public void afterMethod(JoinPoint joinPoint) {
        log.info("final增强，不管是抛出异常或者正常退出都会执行");
    }

    @AfterReturning(value = "pointcut()", returning = "result")
    public void afterReturningMethod(JoinPoint joinPoint, Object result) {
        log.info("end time:{}", System.currentTimeMillis());
    }

    @AfterThrowing(value = "pointcut()", throwing = "e")
    public void afterReturningMethod(JoinPoint joinPoint, Exception e) {
        log.info("调用了异常通知");
    }

    @Around("pointcut()")
    public Object Around(ProceedingJoinPoint pjp) throws Throwable {
        log.info("around执行方法之前");
        Object object = pjp.proceed();
        log.info("around执行方法之后--返回值：" + object);
        return object;
    }
}
