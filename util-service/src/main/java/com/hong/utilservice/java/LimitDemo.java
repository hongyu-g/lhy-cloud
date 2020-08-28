package com.hong.utilservice.java;

import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author liang
 * @description 基于java限流
 * @date 2020/8/28 14:23
 */
public class LimitDemo {


    /**
     * 请求次数
     */
    private int maxCount;

    /**
     * 时间间隔
     */
    private int interval;

    private AtomicInteger atomicInteger = new AtomicInteger(0);

    private long startTime;

    public boolean limit(int maxCount, int interval) {
        atomicInteger.incrementAndGet();
        if (atomicInteger.get() == 1) {
            startTime = System.currentTimeMillis();
        }
        if (System.currentTimeMillis() - startTime > interval * 1000) {
            //重新计时
            startTime = System.currentTimeMillis();
            atomicInteger.set(1);
        }
        if (atomicInteger.get() > maxCount) {
            return false;
        }
        return true;
    }


    private Semaphore semaphore = new Semaphore(5);

    public void demo() {
        for (int i = 0; i < 20; i++) {
            new Thread(() -> {
                boolean flag = false;
                try {
                    flag = semaphore.tryAcquire(100, TimeUnit.MICROSECONDS);
                    if (flag) {
                        //休眠2秒，模拟下单操作
                        System.out.println(Thread.currentThread() + "，尝试下单中。。。。。");
                        TimeUnit.SECONDS.sleep(2);
                    } else {
                        System.out.println(Thread.currentThread() + "，秒杀失败，请稍微重试！");
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    if (flag) {
                        semaphore.release();
                    }
                }
            }).start();
        }
    }


    public static void main(String[] args) {
        LimitDemo demo = new LimitDemo();
        demo.demo();

    }


}
