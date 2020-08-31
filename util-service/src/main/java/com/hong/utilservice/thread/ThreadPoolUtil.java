package com.hong.utilservice.thread;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

public class ThreadPoolUtil {


    /**
     * CPU核数
     */
    private final static int CPU_COUNT = Runtime.getRuntime().availableProcessors();


    private static ThreadFactory getThreadFactory() {
        AtomicInteger atomicInteger = new AtomicInteger();
        return new ThreadFactory() {
            @Override
            public Thread newThread(Runnable r) {
                return new Thread(r, "hong_thread_pool_" + atomicInteger.incrementAndGet());
            }
        };
    }


    private static class ThreadPoolRejectedExecutionHandler implements RejectedExecutionHandler {
        @Override
        public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
            System.out.println("拒绝策略");
        }
    }


    public static ThreadPoolExecutor getThreadPool() {
        BlockingQueue<Runnable> blockingQueue = new LinkedBlockingQueue<>(1000);
        return new ThreadPoolExecutor(CPU_COUNT, CPU_COUNT + 1, 1, TimeUnit.SECONDS,
                blockingQueue, getThreadFactory(), new ThreadPoolRejectedExecutionHandler());
    }


    private ExecutorService executor1 = Executors.newFixedThreadPool(10);

    private ExecutorService executor2 = Executors.newSingleThreadExecutor();

    private ExecutorService executor3 = Executors.newCachedThreadPool();

    private ExecutorService executor4 = Executors.newScheduledThreadPool(10);

}
