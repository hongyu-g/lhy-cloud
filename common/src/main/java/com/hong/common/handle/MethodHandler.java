package com.hong.common.handle;

import com.google.common.collect.Lists;
import com.google.common.util.concurrent.RateLimiter;
import com.hong.common.annotation.AccessLimit;
import com.hong.common.annotation.CheckToken;
import com.hong.common.bean.CurrentUserHolder;
import com.hong.common.data.Response;
import com.hong.common.enums.AccessLimitType;
import com.hong.common.exception.AccessException;
import com.hong.common.exception.AccessLimitException;
import com.hong.common.redis.RedisUtil;
import com.hong.common.util.IPUtil;
import com.hong.common.util.RedisLimiterUtils;
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
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * @author liang
 * @description
 * @date 2020/8/6 13:50
 */
@Slf4j
@Component
@Aspect
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
        int maxCount = accessLimit.maxCount();
        accessRateLimitToken(key, seconds, maxCount);
    }


    /**
     * redis计数器
     *
     * @param key
     * @param seconds
     * @param maxCount
     */
    private void accessLimitRedis(String key, int seconds, int maxCount) {
        long count = redisUtil.incr(key, 1);
        try {
            if (count == 1) {
                redisUtil.expire(key, seconds);
            }
            /**
             * 1、防止出现并发操作未设置超时时间的场景,这样key就是永不过期,存在风险
             * 2、给key加一个时间后缀，这样即时出现永不过期的key也只影响其中某一时间段内的key
             * 3、LUA脚本保证原子性
             */
            if (redisUtil.getExpire(key) == -1) {
                redisUtil.expire(key, seconds);
            }
        } catch (Exception e) {
            redisUtil.expire(key, seconds);
        }
        if (count > maxCount) {
            throw new AccessLimitException("限流，操作频繁，请稍后重试");
        }
    }


    private ConcurrentHashMap<String, RateLimiter> limiterConcurrentHashMap = new ConcurrentHashMap<>();

    /**
     * 令牌桶，适用于单体应用
     *
     * @param key
     * @param seconds
     * @param maxCount
     */
    private void accessRateLimit(String key, int seconds, int maxCount) {
        //每秒生成令牌数
        limiterConcurrentHashMap.putIfAbsent(key, RateLimiter.create(maxCount));
        boolean b = limiterConcurrentHashMap.get(key).tryAcquire(seconds, TimeUnit.SECONDS);
        if (!b) {
            throw new AccessLimitException("限流，操作频繁，请稍后重试");
        }
    }


    @Autowired
    private RedisLimiterUtils redisLimiterUtils;

    private void accessRateLimitToken(String key, int max, int rate) {
        //1秒 2个
        boolean b = redisLimiterUtils.tryAcquire2(key, 100, rate);
        if (!b) {
            throw new AccessLimitException("限流，操作频繁，请稍后重试");
        }
    }

}
