package com.hong.utilservice.thread;

import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author liang
 * @description
 * @date 2020/9/2 15:04
 */
public class SynAndLock {

    private ReentrantLock lock = new ReentrantLock();

    private ThreadPoolExecutor executor = ThreadPoolUtil.getThreadPool();



    public void test() {
        executor.execute(() -> {
            try {
                Thread thread = Thread.currentThread();
                thread.interrupt();
                lock.lockInterruptibly();
                try {
                    System.out.println("线程2");
                } finally {
                    lock.unlock();
                }
            } catch (Exception e) {
                System.out.println("线程2中断");
            }
        });
    }


    /**
     * synchronized 不会中断
     */
    public void testSyn() {
        Thread thread2 = new Thread(() -> {
            synchronized (this) {
                Thread thread = Thread.currentThread();
                System.out.println("线程2");
                thread.interrupt();
            }
        });
        thread2.start();
        System.out.println("中断线程2");
        thread2.interrupt();
    }

    public static void main(String[] args) {
        SynAndLock demo = new SynAndLock();
        demo.testSyn();
    }
}
