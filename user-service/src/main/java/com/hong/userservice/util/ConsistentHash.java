package com.hong.userservice.util;

/**
 * @author liang
 * @description
 * @date 2020/6/11 14:35
 */

import com.google.common.collect.Lists;

import java.util.*;

public class ConsistentHash<T> {


    private final HashFunction hashFunction;

    //每个机器节点，关联的虚拟节点个数
    private final int numberOfReplicas;

    //环形虚拟节点
    private final SortedMap<Long, T> circle = new TreeMap<Long, T>();

    /**
     * @param hashFunction：hash                函数接口
     * @param numberOfReplicas；每个机器节点关联的虚拟节点个数
     * @param nodes:                           真实机器节点
     */
    public ConsistentHash(HashFunction hashFunction, int numberOfReplicas, Collection<T> nodes) {
        this.hashFunction = hashFunction;
        this.numberOfReplicas = numberOfReplicas;
        //遍历真实节点，生成对应的虚拟节点
        for (T node : nodes) {
            add(node);
        }
    }

    /**
     * 增加真实机器节点
     * 由真实节点，计算生成虚拟节点
     *
     * @param node
     */
    public void add(T node) {
        for (int i = 0; i < this.numberOfReplicas; i++) {
            long hashcode = this.hashFunction.hash(node.toString() + "-" + i);
            circle.put(hashcode, node);
        }
    }

    /**
     * 删除真实机器节点
     *
     * @param node
     */
    public void remove(T node) {
        for (int i = 0; i < this.numberOfReplicas; i++) {
            long hashcode = this.hashFunction.hash(node.toString() + "-" + i);
            circle.remove(hashcode);
        }
    }


    /**
     * 生成hash环，通过hash函数，确定节点在hash环上的位置，
     * 沿环的顺时针找到一个虚拟节点
     * 根据数据的key，计算hash值，然后从虚拟节点中，查询取得真实的节点对象
     */
    public T get(String key) {
        if (circle.isEmpty()) {
            return null;
        }
        long hash = hashFunction.hash(key);
        if (!circle.containsKey(hash)) {
            SortedMap<Long, T> tailMap = circle.tailMap(hash);
            hash = tailMap.isEmpty() ? circle.firstKey() : tailMap.firstKey();
        }
        return circle.get(hash);
    }


    public static void main(String[] args) {
        List<String> list = Lists.newArrayList();
        for (int i = 1; i <= 31; i++) {
            list.add("user_" + i);
        }
        ConsistentHash consistentHash = new ConsistentHash(new HashFunction(), 16, list);
        Map<String, Integer> map = new HashMap<>();
        for (int i = 0; i < 50000000; i++) {
            String key = (String) consistentHash.get(i + "");
            if (map.containsKey(key)) {
                map.put(key, map.get(key) + 1);
            } else {
                map.put(key, 1);
            }
        }
        System.out.println(map);
    }
}