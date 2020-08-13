package com.hong.utilservice.thread;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author liang
 * @description
 * @date 2020/7/10 13:48
 */
public class SynDemo5 {

    private ReentrantLock lock = new ReentrantLock();

    private Condition notFull = lock.newCondition();

    private Condition notEmpty = lock.newCondition();

    private ReentrantLock putLock = new ReentrantLock();

    private Condition notFull2 = putLock.newCondition();

    private ReentrantLock takeLock = new ReentrantLock();

    private Condition notEmpty2 = takeLock.newCondition();

    private Queue<Integer> queue = new LinkedList<>();

    private int count;

    /**
     * 添加、删除 不互斥
     */
    class Producer implements Runnable {
        @Override
        public void run() {
            putLock.lock();
            try {
                if (queue.size() >= 10) {
                    notFull2.await();
                }
                count++;
                queue.offer(count);
                System.out.println("入队元素：" + count);
            } catch (Exception e) {
                System.out.println("test");
                e.printStackTrace();
            } finally {
                putLock.unlock();
            }
            takeLock.lock();
            notEmpty2.signalAll();
            takeLock.unlock();
        }
    }


    class Consumer implements Runnable {
        @Override
        public void run() {
            takeLock.lock();
            try {
                if (queue.size() <= 0) {
                    notEmpty2.await();
                }
                Integer count = queue.poll();
                System.out.println("出队元素：" + count);

            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                takeLock.unlock();
            }
            putLock.lock();
            notFull2.signalAll();
            putLock.unlock();
        }
    }

    private String msg = "";

    private class Producer2 implements Runnable {
        @Override
        public void run() {
            try {
                synchronized (msg) {
                    if (queue.size() >= 10) {
                        msg.wait();
                    }
                    count++;
                    queue.offer(count);
                    System.out.println("入队元素：" + count);
                    msg.notifyAll();
                }
            } catch (Exception e) {
                System.out.println("test");
                e.printStackTrace();
            }
        }
    }


    private class Consumer2 implements Runnable {
        @Override
        public void run() {
            try {
                synchronized (msg) {
                    if (queue.size() <= 0) {
                        msg.wait();
                    }
                    Integer count = queue.poll();
                    System.out.println("出队元素：" + count);
                    msg.notifyAll();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        SynDemo5 synDemo = new SynDemo5();
        for (int i = 0; i < 11; i++) {
            new Thread(synDemo.new Producer()).start();
        }
        new Thread(synDemo.new Consumer()).start();
        new Thread(synDemo.new Consumer()).start();
    }
}
