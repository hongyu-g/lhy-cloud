package com.hong.utilservice.thread;

import sun.misc.Unsafe;

import java.lang.reflect.Field;

/**
 * @author liang
 * @description
 * @date 2020/7/10 11:12
 */
public class SynDemo4 {

    private volatile boolean flag;

    private int count;

    private class MyRunnable1 implements Runnable {
        @Override
        public void run() {
            while (!flag) {

            }
            System.out.println("程序执行完成");
        }
    }


    private class MyRunnable2 implements Runnable {
        @Override
        public void run() {
            System.out.println("程序开始执行");
            for (int i = 0; i < 100000; i++) {
                count++;
            }
            flag = true;
        }
    }


    public static void main(String[] args)throws Exception {
        SynDemo4 synDemo = new SynDemo4();
        new Thread(synDemo.new MyRunnable1()).start();
        new Thread(synDemo.new MyRunnable2()).start();
        Field field = Unsafe.class.getDeclaredField("theUnsafe");
        field.setAccessible(true);
        Unsafe unsafe = (Unsafe)field.get(null);
    }

}
