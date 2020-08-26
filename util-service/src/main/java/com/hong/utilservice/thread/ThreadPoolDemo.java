package com.hong.utilservice.thread;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.LockSupport;

/**
 * @author liang
 * @description
 * @date 2020/7/31 10:20
 */
public class ThreadPoolDemo {


    private BlockingQueue blockingQueue = new LinkedBlockingQueue(100);

    private ThreadPoolExecutor executor = new ThreadPoolUtil().getThreadPool();


    class MyRunnable implements Runnable {
        @Override
        public void run() {
            System.out.println("hahhha");
        }
    }


    public void demo() {
        new Wroker(new MyRunnable()).t.start();
    }


    private Thread thread;

    public void lockPark() {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                thread = Thread.currentThread();
                System.out.println(Thread.currentThread().getName() + "：线程阻塞");
                LockSupport.park();
                System.out.println(Thread.currentThread().getName() + "：线程开始执行");
            }
        });

        try {
            Thread.sleep(1000);
        } catch (Exception e) {

        }


        executor.execute(new Runnable() {
            @Override
            public void run() {
                System.out.println(Thread.currentThread().getName() + "：唤醒");
                LockSupport.unpark(thread);
            }
        });

        executor.shutdown();
    }


    public static void main(String[] args) {
        new ThreadPoolDemo().lockPark();
    }


    /**
     * 线程池包装了Wroker
     */
    private class Wroker implements Runnable {

        Thread t;
        Runnable r;

        public Wroker(Runnable r) {
            this.r = r;
            this.t = new Thread(this);
        }

        @Override
        public void run() {
            System.out.println("test");
            r.run();
        }

    }
}
