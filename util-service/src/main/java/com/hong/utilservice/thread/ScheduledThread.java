package com.hong.utilservice.thread;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author liang
 * @description
 * @date 2020/9/1 18:52
 */
public class ScheduledThread {


    /**
     * 执行延迟任务和周期性任务
     */
    private ScheduledThreadPoolExecutor scheduledThreadPoolExecutor = new ScheduledThreadPoolExecutor(1);


    private Timer timer = new Timer();

    public void timerTest() {
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                System.out.println("test");
            }
        }, 3 * 1000);
    }

    public void demo() {
        scheduledThreadPoolExecutor.schedule(() -> {
            System.out.println("test");
        }, 2, TimeUnit.SECONDS);

        scheduledThreadPoolExecutor.scheduleAtFixedRate(() -> {
            System.out.println("enenn");
        }, 1, 1, TimeUnit.SECONDS);

        scheduledThreadPoolExecutor.scheduleWithFixedDelay(() -> {
            System.out.println("hahahha");
        }, 1, 1, TimeUnit.SECONDS);

    }

    public static void main(String[] args) {
        ScheduledThread thread = new ScheduledThread();
        thread.timerTest();
    }
}
