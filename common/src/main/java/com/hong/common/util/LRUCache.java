package com.hong.common.util;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * @author liang
 * @description
 * @date 2020/9/1 15:34
 */
public class LRUCache {

    private Map<Integer, Integer> mapCache;

    private Queue<Integer> keyQueue;

    private int capacity;

    private Map<Integer, Integer> linkedHashMap;

    public LRUCache(int capacity) {
        this.capacity = capacity;
        mapCache = new ConcurrentHashMap<>(capacity);
        keyQueue = new ConcurrentLinkedQueue<>();
        linkedHashMap = new LinkedHashMap<Integer, Integer>(capacity, 0.75f, true) {
            @Override
            protected boolean removeEldestEntry(Map.Entry eldest) {
                return capacity < linkedHashMap.size();
            }
        };
    }


    public int get(int key) {
        return linkedHashMap.getOrDefault(key, -1);
    }

    public void put(int key, int value) {
        linkedHashMap.put(key, value);
    }


    public int getCon(int key) {
        if (mapCache.containsKey(key)) {
            keyQueue.remove(key);
            keyQueue.add(key);
            mapCache.get(key);
            return mapCache.get(key);
        }
        return -1;
    }

    public void putCon(int key, int value) {
        if (mapCache.containsKey(key)) {
            mapCache.put(key, value);
            keyQueue.remove(key);
            keyQueue.add(key);
            return;
        }
        if (mapCache.size() >= capacity) {
            Integer oldestKey = keyQueue.poll();
            if (oldestKey != null) {
                mapCache.remove(oldestKey);
            } else {
                return;
            }
        }
        mapCache.put(key, value);
        keyQueue.remove(key);
        keyQueue.add(key);
    }




    public static void main(String[] args) {
        LRUCache cache = new LRUCache(2);
        cache.put(2, 6);
        cache.put(1, 2);
        cache.put(2, 2);

    }
}
