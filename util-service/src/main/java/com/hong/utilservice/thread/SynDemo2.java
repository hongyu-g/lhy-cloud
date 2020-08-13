package com.hong.utilservice.thread;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author liang
 * @description
 * @date 2020/7/9 15:58
 */
public class SynDemo2 {


    private ReentrantLock lock = new ReentrantLock();

    private Condition A = lock.newCondition();

    private Condition B = lock.newCondition();

    private Condition C = lock.newCondition();

    private int state;


    class MyRunnable implements Runnable {
        @Override
        public void run() {
            for (int i = 0; i < 10; i++) {
                lock.lock();
                try {
                    if (state != 0) {
                        A.await();
                    }
                    System.out.println("A");
                    state = 1;
                    B.signalAll();
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    lock.unlock();
                }
            }
        }
    }

    class MyRunnable2 implements Runnable {
        @Override
        public void run() {
            for (int i = 0; i < 10; i++) {
                lock.lock();
                try {
                    if (state != 1) {
                        B.await();
                    }
                    System.out.println("B");
                    state = 2;
                    C.signalAll();
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    lock.unlock();
                }

            }
        }

    }

    class MyRunnable3 implements Runnable {
        @Override
        public void run() {
            for (int i = 0; i < 10; i++) {
                lock.lock();
                try {
                    if (state != 2) {
                        C.await();
                    }
                    System.out.println("C");
                    state = 3;
                    A.signalAll();
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    lock.unlock();
                }
            }
        }

    }


    public static void main(String[] args) {
        SynDemo2 demo = new SynDemo2();
        new Thread(demo.new MyRunnable()).start();
        new Thread(demo.new MyRunnable2()).start();
        new Thread(demo.new MyRunnable3()).start();

    }

}
