package com.hong.utilservice.util;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author liang
 * @description
 * @date 2020/6/21 11:21
 */
public class ThreadPoolDemo {

    private static AtomicInteger atomicInteger = new AtomicInteger();

    private static ThreadFactory getThreadFactory() {
        return (Runnable r) -> {
            Thread thread = new Thread(r);
            thread.setName("hong_thread_" + atomicInteger.incrementAndGet());
            return thread;
        };
    }
    private static ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(5, 8, 1,
            TimeUnit.SECONDS, new ArrayBlockingQueue<>(100), getThreadFactory(), new ThreadPoolExecutor.CallerRunsPolicy());

    public static ThreadPoolExecutor getThreadPoolExecutor() {
        return threadPoolExecutor;
    }

    public static void setThreadPoolExecutor(ThreadPoolExecutor threadPoolExecutor) {
        ThreadPoolDemo.threadPoolExecutor = threadPoolExecutor;
    }
}
