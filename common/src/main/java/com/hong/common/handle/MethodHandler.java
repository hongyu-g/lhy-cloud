package com.hong.common.handle;

import com.google.common.collect.Lists;
import com.hong.common.annotation.AccessLimit;
import com.hong.common.annotation.CheckToken;
import com.hong.common.bean.CurrentUserHolder;
import com.hong.common.data.Response;
import com.hong.common.enums.AccessLimitType;
import com.hong.common.exception.AccessException;
import com.hong.common.exception.AccessLimitException;
import com.hong.common.redis.RedisUtil;
import com.hong.common.util.IPUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author liang
 * @description
 * @date 2020/8/6 13:50
 */
@Slf4j
//@Component
//@Aspect
public class MethodHandler {

    @Autowired
    private RedisUtil redisUtil;


    @Pointcut("execution(public * com.hong..*Controller.*(..))")
    public void pointcut() {

    }

    /**
     * @return
     * @throws Exception
     */
    @Around("pointcut()")
    public Response proceed(ProceedingJoinPoint pjp) {
        MethodSignature methodSignature = (MethodSignature) pjp.getSignature();
        Method method = methodSignature.getMethod();
        String methodName = method.getName();
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = null;
        HttpServletResponse response = null;
        try {
            if (attributes != null) {
                request = attributes.getRequest();
                response = attributes.getResponse();
            }
            //参数信息
            Object[] args = pjp.getArgs();
            List<Object> logArgs = Lists.newArrayList();
            if (args != null) {
                logArgs = Lists.newArrayList(args).stream().filter(
                        arg -> (!(arg instanceof HttpServletRequest) && !(arg instanceof HttpServletResponse)))
                        .collect(Collectors.toList());
            }
            log.info("方法开始执行，名称:{},参数:{}", methodName, logArgs);
            checkToken(method, request);
            accessLimit(method, request);
            long start = System.currentTimeMillis();
            Object object = pjp.proceed();
            log.info("方法执行结束，名称:{},用时:{}", methodName,
                    System.currentTimeMillis() - start + "ms");
            return new Response<>().success(object);
        } catch (Throwable e) {
            log.error("方法发生异常，方法名称:{},异常:{}", methodName, e);
            if (e instanceof AccessException) {
                return new Response<>(401, null, e.getMessage());
            }
            if (e instanceof AccessLimitException) {
                return new Response<>(500, null, e.getMessage());
            }
            return new Response<>().error("请稍后重试");
        } finally {
            CurrentUserHolder.remove();
        }
    }


    @Pointcut("@annotation(com.hong.common.annotation.AccessLimit)")
    public void pointcut2() {

    }

    @Before("pointcut2()")
    public void before(JoinPoint joinPoint) {
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        Method method = methodSignature.getMethod();
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        accessLimit(method, request);
    }


    private void checkToken(Method method, HttpServletRequest request) {
        CheckToken checkToken = method.getAnnotation(CheckToken.class);
        if (checkToken == null || !checkToken.required()) {
            return;
        }
        String token = request.getHeader("token");
        if (StringUtils.isBlank(token)) {
            throw new AccessException("无token，请重新登录");
        }
        String userId = String.valueOf(redisUtil.get(token));
        if (userId == null) {
            throw new AccessException("用户不存在，请重新登录");
        }
        CurrentUserHolder.setHolder(Long.valueOf(userId));
    }


    private void accessLimit(Method method, HttpServletRequest request) {
        AccessLimit accessLimit = method.getAnnotation(AccessLimit.class);
        if (accessLimit == null || request == null || !accessLimit.check()) {
            return;
        }
        //key 定义
        String key = CurrentUserHolder.getHolder() + request.getRequestURI();
        if (accessLimit.limitType() == AccessLimitType.CUSTOMIZE) {
            key = (String) request.getAttribute("access_limit_customize");
        }
        if (accessLimit.limitType() == AccessLimitType.IP) {
            key = IPUtil.getIpAddr(request);
        }
        int seconds = accessLimit.seconds();
        long count = redisUtil.incr(key, 1);
        if (count == 1) {
            redisUtil.set(key, 1, seconds);
            log.info("初始化redis数据");
        }
        int maxCount = accessLimit.maxCount();
        if (count > maxCount) {
            throw new AccessLimitException("限流，操作频繁，请稍后重试");
        }
    }


}
