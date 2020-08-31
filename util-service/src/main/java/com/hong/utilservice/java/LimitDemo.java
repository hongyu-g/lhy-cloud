package com.hong.utilservice.java;

import com.google.common.util.concurrent.RateLimiter;
import com.hong.utilservice.thread.ThreadPoolUtil;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Semaphore;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author liang
 * @description
 * @date 2020/8/28 14:23
 */
public class LimitDemo {


    /**
     * 请求次数
     */
    private int maxCount;

    /**
     * 时间间隔
     */
    private int interval;

    private AtomicInteger atomicInteger = new AtomicInteger(0);

    private long startTime;

    private ThreadPoolExecutor executor = ThreadPoolUtil.getThreadPool();


    /**
     * 基于java限流
     * AtomicInteger + time
     *
     * @param maxCount
     * @param interval
     * @return
     */
    public boolean limit(int maxCount, int interval) {
        atomicInteger.incrementAndGet();
        if (atomicInteger.get() == 1) {
            startTime = System.currentTimeMillis();
        }
        if (System.currentTimeMillis() - startTime > interval * 1000) {
            //重新计时
            startTime = System.currentTimeMillis();
            atomicInteger.set(1);
        }
        if (atomicInteger.get() > maxCount) {
            return false;
        }
        return true;
    }


    private Semaphore semaphore = new Semaphore(5);

    /**
     * 基于java限流：信号量
     * 控制同时访问接口的数量
     */
    public void semaphoreTest() {
        for (int i = 0; i < 20; i++) {
            executor.execute(() -> {
                boolean flag = false;
                try {
                    flag = semaphore.tryAcquire(100, TimeUnit.MILLISECONDS);
                    if (flag) {
                        //休眠2秒，模拟下单操作
                        System.out.println(Thread.currentThread() + "，尝试下单中。。。。。");
                        Thread.sleep(2000);
                    } else {
                        System.out.println(Thread.currentThread() + "，秒杀失败，请稍微重试！");
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    if (flag) {
                        semaphore.release();
                    }
                }
            });
        }
    }

    /**
     * 每秒生成的令牌数
     */
    private RateLimiter rateLimiter = RateLimiter.create(1);


    /**
     * 令牌桶：线程拿到令牌才能执行，以一定的速率访问接口
     */
    private void accessRateLimit() {
        //每秒生成令牌数
        for (int i = 0; i < 20; i++) {
            executor.execute(() -> {
                try {
                    //在指定时间内没有获取到令牌，直接放弃
                    boolean b = rateLimiter.tryAcquire(500, TimeUnit.MILLISECONDS);
                    if (b) {
                        System.out.println(Thread.currentThread() + "，执行中。。。。。");
                    } else {
                        System.out.println(Thread.currentThread() + "，秒杀失败，请稍微重试！");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

            });
        }
        executor.shutdown();
    }


    public static void main(String[] args) {
        LimitDemo demo = new LimitDemo();
        demo.accessRateLimit();
    }


}
