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
public class ConcurrentThreadTest {

    private ReentrantLock lock = new ReentrantLock();

    private Condition condition = lock.newCondition();

    private ReentrantReadWriteLock readWriteLock = new ReentrantReadWriteLock();

    ReentrantReadWriteLock.ReadLock readLock = readWriteLock.readLock();

    ReentrantReadWriteLock.WriteLock writeLock = readWriteLock.writeLock();

    private CountDownLatch countDownLatch = new CountDownLatch(3);

    private CyclicBarrier cyclicBarrier = new CyclicBarrier(2, new Runnable() {
        @Override
        public void run() {
            System.out.println("到齐了");
        }
    });

    /**
     * 控制线程并发数
     */
    private Semaphore semaphore = new Semaphore(2);

    private Exchanger exchanger = new Exchanger();

    private AtomicInteger atomicInteger = new AtomicInteger();

    private int num;

    class lockDemo implements Runnable {
        @Override
        public void run() {
            lock.lock();
            try {
                System.out.println(Thread.currentThread().getName() + "：开始执行");
                Thread.sleep(1000);
                num++;
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
        }
    }

    class conditionDemo implements Runnable {
        @Override
        public void run() {
            lock.lock();
            try {
                System.out.println(Thread.currentThread().getName() + "：开始等待1");
                condition.await();
                System.out.println(Thread.currentThread().getName() + "：开始执行1");
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
        }
    }


    class conditionDemoSignal implements Runnable {
        @Override
        public void run() {
            lock.lock();
            try {
                System.out.println(Thread.currentThread().getName() + "：唤醒1");
                Thread.sleep(1000);
                condition.signal();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
        }
    }


    class CDR implements Runnable {
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

    class CBR implements Runnable {
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


    class SPR implements Runnable {
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

    private static Thread thread;

    class LSR implements Runnable {
        @Override
        public void run() {
            thread = Thread.currentThread();
            System.out.println("线程1阻塞");
            //while (!Thread.interrupted()) {
            LockSupport.park();
            //}
            System.out.println("线程1被唤醒");
        }
    }


    private static ThreadPoolExecutor executor = ThreadPoolUtil.getThreadPool();

    public static void main(String[] args) throws Exception {
        ConcurrentThreadTest concurrentThreadTest = new ConcurrentThreadTest();
        for (int i = 0; i < 1; i++) {
            executor.execute(concurrentThreadTest.new conditionDemo());
        }
        for (int i = 0; i < 1; i++) {
            executor.execute(concurrentThreadTest.new conditionDemoSignal());
        }
        executor.shutdown();
    }
}
