package com.hong.utilservice.thread;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author liang
 * @description
 * @date 2020/7/17 11:05
 */
public class WaitAndSignalDemo {

    private ReentrantLock lock = new ReentrantLock();

    private Condition condition = lock.newCondition();


    class MyRunnable implements Runnable {
        @Override
        public void run() {
            lock.lock();
            try {
                System.out.println("线程1等待");
                condition.await();
                System.out.println("线程1被唤醒");
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
        }
    }


    class MyRunnable2 implements Runnable {
        @Override
        public void run() {
            lock.lock();
            try {
                System.out.println("线程2开始执行");
                Thread.sleep(1000);
                condition.signal();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
        }
    }

    public static void main(String[] args) throws Exception {
        WaitAndSignalDemo demo = new WaitAndSignalDemo();
        Thread t1 = new Thread(demo.new MyRunnable());
        t1.start();
        new Thread(demo.new MyRunnable2()).start();
    }

}

