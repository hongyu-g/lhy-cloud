package com.hong.utilservice.util;

import com.google.common.collect.Lists;

import java.util.List;

/**
 * @author liang
 * @description
 * @date 2020/7/21 11:47
 */
public class ObjectClassCast {

    public static <T> T cast(Object object, Class<T> cl) {
        if (object == null) {
            return null;
        }
        if (cl.isInstance(object)) {
            return cl.cast(object);
        }
        return null;
    }

    public static void main(String[] args) {
        List<Integer> list = Lists.newArrayList();
        list.add(1);
        System.out.println(ObjectClassCast.cast(list, List.class));
    }
}
