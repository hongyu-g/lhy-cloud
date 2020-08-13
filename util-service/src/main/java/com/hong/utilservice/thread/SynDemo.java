package com.hong.utilservice.thread;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.LockSupport;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * @author liang
 * @description
 * @date 2020/7/8 18:27
 */
public class SynDemo {

    private static ReentrantLock lock = new ReentrantLock();

    private static Condition condition = lock.newCondition();

    private ReentrantReadWriteLock readWriteLock = new ReentrantReadWriteLock();

    ReentrantReadWriteLock.ReadLock readLock = readWriteLock.readLock();

    ReentrantReadWriteLock.WriteLock writeLock = readWriteLock.writeLock();

    private static CountDownLatch countDownLatch = new CountDownLatch(3);

    private static CyclicBarrier cyclicBarrier = new CyclicBarrier(2, new Runnable() {
        @Override
        public void run() {
            System.out.println("到齐了");
        }
    });

    /**
     * 控制线程并发数
     */
    private static Semaphore semaphore = new Semaphore(2);

    private static Exchanger exchanger = new Exchanger();

    private static AtomicInteger atomicInteger = new AtomicInteger();


    static class CDR implements Runnable {
        @Override
        public void run() {
            try {
                System.out.println(Thread.currentThread().getName() + ":执行");
                Thread.sleep(1000);
                System.out.println(Thread.currentThread().getName() + ":执行结束," + atomicInteger.incrementAndGet());
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                countDownLatch.countDown();
            }
        }
    }

    static class CBR implements Runnable {
        @Override
        public void run() {
            try {
                System.out.println(Thread.currentThread().getName() + ":到达");
                cyclicBarrier.await();
                System.out.println(Thread.currentThread().getName() + ":执行");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    static class SPR implements Runnable {
        @Override
        public void run() {
            try {
                semaphore.acquire();
                System.out.println(Thread.currentThread().getName() + ":执行");
                Thread.sleep(3000);

            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                semaphore.release();
            }
        }
    }

    static class LSR implements Runnable {
        @Override
        public void run() {
            System.out.println("线程1阻塞");
            while (!Thread.interrupted()) {
                LockSupport.park();
            }
            System.out.println("线程1被唤醒");
        }
    }


    public static void main(String[] args) throws Exception {
        Thread t1 = new Thread(new CDR());
        t1.start();
        new Thread(new CDR()).start();
       // new Thread(new CDR()).start();
        //t1.interrupt();
        countDownLatch.await(6, TimeUnit.SECONDS);
        System.out.println("主线程开始执行");
    }
}
