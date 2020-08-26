package com.hong.utilservice.thread;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

public class ThreadPoolUtil {

    private AtomicInteger atomicInteger = new AtomicInteger();

    private BlockingQueue<Runnable> blockingQueue = new LinkedBlockingQueue<>(1000);

    /**
     * CPU核数
     */
    private final static int CPU_COUNT = Runtime.getRuntime().availableProcessors();


    private ThreadFactory getThreadFactory() {
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


    public ThreadPoolExecutor getThreadPool() {
        return new ThreadPoolExecutor(CPU_COUNT, CPU_COUNT + 1, 1, TimeUnit.SECONDS,
                blockingQueue, getThreadFactory(), new ThreadPoolRejectedExecutionHandler());
    }

}
