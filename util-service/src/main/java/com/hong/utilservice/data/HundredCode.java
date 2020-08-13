package com.hong.utilservice.data;

import java.util.HashSet;
import java.util.Set;

public class HundredCode {

    /**
     * 二进制位中不同的位数
     * 移位运算
     *
     * @param x
     * @param y
     * @return
     */
    public static int hammingDistance(int x, int y) {
        int num = x ^ y;
        int distance = 0;
        while (num != 0) {
            if ((num & 1) == 1) {
                distance += 1;
            }
            num = num >> 1;
        }
        return distance;
    }

    public int singleNumber(int[] nums) {
        int result = 0;
        for (int num : nums) {
            result ^= num;
        }
        return result;
    }

    /**
     * 判断是否存在重复元素
     * @param nums
     * @return
     */
    public static boolean containsDuplicate(int[] nums) {
        Set<Integer> set = new HashSet<>();
        for(int num :nums){
            if(!set.add(num)){
                return true;
            }
        }
        return false;
    }


    public static void main(String[] args) {
        int[] nums = {2,2,1,1,1,2,2};
        System.out.println(containsDuplicate(nums));
    }


}
