package com.hong.utilservice.thread;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class Demo {

    private static AtomicInteger atomicInteger = new AtomicInteger();

    private static ThreadPoolExecutor poolExecutor = new ThreadPoolExecutor(2, 3, 1, TimeUnit.SECONDS,
            new ArrayBlockingQueue<>(100), new ThreadFactory() {
        @Override
        public Thread newThread(Runnable r) {
            Thread t = new Thread(r);
            t.setName("hong_thread_pool_" + atomicInteger.incrementAndGet());
            return t;
        }
    }, new ThreadPoolExecutor.CallerRunsPolicy());

    static class MyRunnable implements Runnable {

        private Thread thread;

        public void setInterrupt() {
            thread.interrupt();
        }

        @Override
        public void run() {
            thread = Thread.currentThread();
            try {
                while (!Thread.interrupted()) {

                }
            } catch (Exception e) {
                System.out.println("error");
            }
            System.out.println("end");
        }
    }

    public static void main(String[] args) throws Exception {
        Runnable job = new MyRunnable();
        poolExecutor.execute(job);
        Thread.sleep(1000);
        ((MyRunnable) job).setInterrupt();
    }
}
