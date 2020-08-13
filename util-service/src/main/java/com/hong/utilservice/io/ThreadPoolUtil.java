package com.hong.utilservice.io;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author liang
 * @description
 * @date 2020/7/1 11:22
 */
public class ThreadPoolUtil {

    private static AtomicInteger atomicInteger = new AtomicInteger();


    private static ThreadFactory getThreadFactory() {
        return new ThreadFactory() {
            @Override
            public Thread newThread(Runnable r) {
                Thread thread = new Thread(r);
                thread.setName("hong_thread_" + atomicInteger.incrementAndGet());
                return thread;
            }
        };
    }

    public static ThreadPoolExecutor getThreadPool() {
        BlockingQueue blockingQueue = new LinkedBlockingQueue<>(100);
        return new ThreadPoolExecutor(3, 3, 3, TimeUnit.SECONDS, blockingQueue,
                getThreadFactory(), new ThreadPoolExecutor.CallerRunsPolicy());
    }
}
