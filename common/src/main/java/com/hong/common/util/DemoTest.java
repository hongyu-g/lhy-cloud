package com.hong.common.util;

import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author liang
 * @description
 * @date 2020/9/1 11:32
 */
public class DemoTest {

    private static ThreadPoolExecutor executor = ThreadPoolUtil.getThreadPool();

    private static String key = "hongyu";
    private static String key2 = "hongyu2";

    private static JavaCache cache = JavaCache.getInstance();

    private static AtomicInteger atomicInteger = new AtomicInteger(1);

    public static void main(String[] args) {
        for (int i = 0; i < 10; i++) {
            executor.execute(() -> {
                cache.put(key + atomicInteger.getAndIncrement(), "ok", 10 * 1000);
            });
        }
        executor.shutdown();
    }
}
