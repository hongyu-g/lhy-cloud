package com.hong.utilservice.thread;

import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author liang
 * @description
 * @date 2020/9/1 18:52
 */
public class ScheduledThread {

    private ScheduledThreadPoolExecutor scheduledThreadPoolExecutor = new ScheduledThreadPoolExecutor(1);


    public void demo() {
        scheduledThreadPoolExecutor.schedule(() -> {
            System.out.println("test");
        }, 1, TimeUnit.SECONDS);
    }

    public static void main(String[] args) {
        ScheduledThread thread = new ScheduledThread();
        thread.demo();
    }
}
