package com.hong.utilservice.java;

import java.util.HashMap;
import java.util.LinkedHashMap;

/**
 * @author liang
 * @description
 * @date 2020/9/1 17:47
 */
public class MapTest {

    private LinkedHashMap<Integer, Integer> linkedHashMap = new LinkedHashMap<>(10, 0.75f, true);

    private HashMap<Integer, Integer> hashMap = new HashMap<>();


    /**
     * 访问顺序
     */
    public void linkedHashMapTest() {
        linkedHashMap.put(1, 1);
        linkedHashMap.put(112, 2);
        System.out.println(linkedHashMap);
        linkedHashMap.get(1);
        System.out.println(linkedHashMap);
    }

    public void hashMapTest() {
        hashMap.put(1, 1);
        hashMap.put(112, 2);
        System.out.println(hashMap);
    }

    public static void main(String[] args) {
        new MapTest().linkedHashMapTest();
    }
}
