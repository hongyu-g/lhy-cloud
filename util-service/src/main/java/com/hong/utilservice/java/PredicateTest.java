package com.hong.utilservice.java;

import java.util.function.Predicate;

/**
 * @author liang
 * @description
 * @date 2020/7/16 17:53
 */
public class PredicateTest {


    public static void main(String[] args) {
        /**
         * 判断是否大于7
         */
        Predicate<Integer> test = x -> x > 7;
        System.out.println(test.test(11));

        /**
         * 判断是否大于7 && 判断是否小于20
         */
        Predicate<Integer> test2 = test.and(x -> x < 20);
        System.out.println(test2.test(12));
    }
}
