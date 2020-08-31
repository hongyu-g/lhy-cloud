package com.hong.utilservice.thread;

import java.util.concurrent.ThreadPoolExecutor;

/**
 * @author liang
 * @description
 * @date 2020/8/26 15:42
 */
public class ThreadLocalDemo {

    private static ThreadLocal<Integer> userThreadLocal = new ThreadLocal<>();

    private ThreadPoolExecutor executor =ThreadPoolUtil.getThreadPool();


    class MyRunnable implements Runnable {
        @Override
        public void run() {
            userThreadLocal.set(1);
            try {
                Thread.sleep(1000);
            } catch (Exception e) {
                e.printStackTrace();
            }
            System.out.println(userThreadLocal.get());
            userThreadLocal.remove();
        }
    }

    class MyRunnable2 implements Runnable {

        @Override
        public void run() {
            userThreadLocal.set(10);
            System.out.println(userThreadLocal.get());
            userThreadLocal.remove();
        }
    }

    public void demo() {
        executor.execute(new MyRunnable());
        executor.execute(new MyRunnable2());
        executor.shutdown();
    }

    public static void main(String[] args) {
        ThreadLocalDemo demo = new ThreadLocalDemo();
        demo.demo();
    }
}
