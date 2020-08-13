package com.hong.utilservice.thread;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author liang
 * @description
 * @date 2020/7/31 10:20
 */
public class ThreadPoolDemo {


    private static AtomicInteger atomicInteger = new AtomicInteger(10);


    private BlockingQueue blockingQueue = new ArrayBlockingQueue(10);

    private BlockingQueue blockingQueue2 = new LinkedBlockingQueue(100);


    private ThreadPoolExecutor poolExecutor = new ThreadPoolExecutor(2, 3, 1, TimeUnit.SECONDS,
            new ArrayBlockingQueue<>(100), new ThreadFactory() {
        @Override
        public Thread newThread(Runnable r) {
            Thread t = new Thread(r);
            t.setName("hong_thread_pool_" + atomicInteger.incrementAndGet());
            return t;
        }
    }, new ThreadPoolExecutor.CallerRunsPolicy());

    private ExecutorService executor1 = Executors.newFixedThreadPool(10);

    private ExecutorService executor2 = Executors.newSingleThreadExecutor();

    private ExecutorService executor3 = Executors.newCachedThreadPool();

    private ExecutorService executor4 = Executors.newScheduledThreadPool(10);



    /**
     * 高位表示线程池状态，低位表示活动线程数
     */
    private final AtomicInteger ctl = new AtomicInteger(ctlOf(RUNNING, 0));
    private static final int COUNT_BITS = Integer.SIZE - 3;
    private static final int CAPACITY = (1 << COUNT_BITS) - 1;

    private static final int RUNNING = -1 << COUNT_BITS;
    private static final int SHUTDOWN = 0 << COUNT_BITS;
    private static final int STOP = 1 << COUNT_BITS;
    private static final int TIDYING = 2 << COUNT_BITS;
    private static final int TERMINATED = 3 << COUNT_BITS;

    private static int runStateOf(int c) {
        System.out.println("runStateOf c:" + Integer.toBinaryString(c));
        System.out.println("runStateOf CAPACITY:" + Integer.toBinaryString(~CAPACITY));
        return c & ~CAPACITY;
    }

    private static int workerCountOf(int c) {
        System.out.println("workerCountOf c:" + Integer.toBinaryString(c));
        System.out.println("workerCountOf CAPACITY:" + Integer.toBinaryString(CAPACITY));
        return c & CAPACITY;

    }

    private static int ctlOf(int rs, int wc) {
        return rs | wc;
    }

    public static void main(String[] args) {
        System.out.println(Integer.toBinaryString(CAPACITY));
        System.out.println(Integer.toBinaryString(RUNNING));
        System.out.println(Integer.toBinaryString(SHUTDOWN));
        System.out.println(Integer.toBinaryString(STOP));
        System.out.println(Integer.toBinaryString(TIDYING));
        System.out.println(Integer.toBinaryString(TERMINATED));

        ThreadPoolDemo demo = new ThreadPoolDemo();
        System.out.println(ThreadPoolDemo.workerCountOf(demo.ctl.get()));
        System.out.println(Integer.toBinaryString(ThreadPoolDemo.runStateOf(demo.ctl.get())));
    }
}
