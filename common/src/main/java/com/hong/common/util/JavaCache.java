package com.hong.common.util;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.*;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * @author liang
 * @description java实现本地缓存，过期时间
 * @date 2020/9/1 11:06
 */
public class JavaCache {


    /**
     * ConcurrentHashMap + ConcurrentLinkedQueue（hash+链表）
     * LinkedHashMap +锁
     */

    //默认的缓存容量
    private static int DEFAULT_CAPACITY = 3;
    //最大容量
    private static int MAX_CAPACITY = 5;

    private static Map<String, CacheData> map = new ConcurrentHashMap<>(DEFAULT_CAPACITY);

    private ReentrantReadWriteLock lock = new ReentrantReadWriteLock();

    private ReentrantReadWriteLock.ReadLock readLock = lock.readLock();

    private ReentrantReadWriteLock.WriteLock writeLock = lock.writeLock();

    private ReentrantLock reentrantLock = new ReentrantLock();

    private static Map<String, CacheData> linkedHashMap = new LinkedHashMap<String, CacheData>(DEFAULT_CAPACITY, 0.75f, true) {

        @Override
        protected boolean removeEldestEntry(Map.Entry eldest) {
            System.out.println("缓存淘汰");
            return MAX_CAPACITY < linkedHashMap.size();
        }
    };

    /**
     * 刷新缓存的频率
     */
    private static final int MONITOR_DURATION = 2;


    private static ConcurrentLinkedQueue<String> keysQueue = new ConcurrentLinkedQueue<>();

    private ScheduledExecutorService scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();


    public void put(String key, String value, long expire) {
        reentrantLock.lock();
        try {
            CacheData data = new CacheData();
            data.setKey(key);
            data.setValue(value);
            data.setExpireTime(expire + System.currentTimeMillis());
            linkedHashMap.put(key, data);
            if (expire > 0) {
                removeAfterExpireTime(key, expire);
            }
        } finally {
            reentrantLock.unlock();
        }
    }


    /**
     * 过期删除
     */
    public CacheData get(String key) {
        reentrantLock.lock();
        try {
            return linkedHashMap.getOrDefault(key, new CacheData());
        } finally {
            reentrantLock.unlock();
        }
    }

    private void removeAfterExpireTime(String key, long expireTime) {
        scheduledExecutorService.schedule(() -> {
            System.out.println("过期后清除该键值对");
            map.remove(key);
            keysQueue.remove(key);
        }, expireTime, TimeUnit.MILLISECONDS);
    }

    /**
     * 缓存淘汰 最近最少使用策略
     */
    private void cacheDelete() {
        System.out.println("缓存淘汰");
        removeOldestKey();
    }


    /**
     * 将元素添加到队列的尾部(put/get的时候执行)
     */
    private void moveToTailOfQueue(String key) {
        keysQueue.remove(key);
        keysQueue.add(key);
    }

    /**
     * 移除队列头部的元素以及其对应的缓存 (缓存容量已满的时候执行)
     */
    private void removeOldestKey() {
        String oldestKey = keysQueue.poll();
        if (oldestKey != null) {
            map.remove(oldestKey);
        }
    }

    static class ExpireCache implements Runnable {
        @Override
        public void run() {
            while (true) {
                try {
                    TimeUnit.SECONDS.sleep(MONITOR_DURATION);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                Set<Map.Entry<String, CacheData>> mapData = map.entrySet();
                for (Map.Entry<String, CacheData> entry : mapData) {
                    CacheData data = entry.getValue();
                    if (data.getExpireTime() - System.currentTimeMillis() <= 0) {
                        map.remove(entry.getKey());
                        keysQueue.remove(entry.getKey());
                        System.out.println("清除缓存完成");
                        System.out.println(map);
                    }
                }
            }
        }
    }


    /**
     * 监控线程，处理过期数据
     */
    static {
//        ThreadPoolExecutor executor = ThreadPoolUtil.getThreadPool();
//        executor.execute(new ExpireCache());
//        executor.shutdown();
        //new Thread(new ExpireCache()).start();
    }


    private JavaCache() {
    }

    private static class CacheLocal {
        private static JavaCache cache = new JavaCache();
    }

    public static JavaCache getInstance() {
        return CacheLocal.cache;
    }


    private class CacheData {

        private String key;

        private String value;

        /**
         * 存活时间
         */
        private long expireTime;


        private String getValue() {
            return value;
        }

        private void setValue(String value) {
            this.value = value;
        }

        public String getKey() {
            return key;
        }

        public void setKey(String key) {
            this.key = key;
        }

        public long getExpireTime() {
            return expireTime;
        }

        public void setExpireTime(long expireTime) {
            this.expireTime = expireTime;
        }


        @Override
        public String toString() {
            return "CacheData{" +
                    "key='" + key + '\'' +
                    ", value='" + value + '\'' +
                    ", expireTime=" + expireTime +
                    '}';
        }
    }
}

