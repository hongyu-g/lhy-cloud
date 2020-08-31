package com.hong.utilservice.thread;

import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author liang
 * @description
 * @date 2020/7/9 15:58
 */
public class ABC {


    /**
     * 输出ABC
     */
    private ReentrantLock lock = new ReentrantLock();

    private Condition A = lock.newCondition();

    private Condition B = lock.newCondition();

    private Condition C = lock.newCondition();

    private int state;

    class A implements Runnable {
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


    class B implements Runnable {
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

    class C implements Runnable {
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


    class ATest implements Runnable {
        @Override
        public void run() {
            lock.lock();
            try {
                A.signal();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
        }
    }

    public static void main(String[] args) {
        ABC demo = new ABC();
        ThreadPoolExecutor executor = ThreadPoolUtil.getThreadPool();
        executor.execute(demo.new A());
//        executor.execute(demo.new B());
//        executor.execute(demo.new C());
        executor.execute(demo.new ATest());
        executor.shutdown();
    }

}
