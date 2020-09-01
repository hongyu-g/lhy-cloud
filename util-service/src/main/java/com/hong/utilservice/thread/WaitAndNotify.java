package com.hong.utilservice.thread;

public class WaitAndNotify {


    public static void main(String[] args) {
        WaitAndNotify demo = new WaitAndNotify();
        new Thread(() -> {
            synchronized (demo) {
                try {
                    System.out.println("线程1开始等待。。。");
                    demo.wait();
                    System.out.println("线程1被唤醒。。。");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();


        new Thread(() -> {
            synchronized (demo) {
                try {
                    System.out.println("线程2开始等待。。。");
                    demo.wait();
                    System.out.println("线程2被唤醒。。。");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();


        new Thread(() -> {
            synchronized (demo) {
                try {
                    System.out.println("线程3开始唤醒全部。。。");
                    demo.notifyAll();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}
