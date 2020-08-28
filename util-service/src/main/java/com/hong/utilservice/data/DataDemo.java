package com.hong.utilservice.data;

import java.util.ArrayList;
import java.util.List;

/**
 * @author liang
 * @description
 * @date 2020/8/28 11:30
 */
public class DataDemo {


    /**
     * 19
     * 1*1 + 9*9 = 82
     * 8*8 + 2*2 = 68
     * 6*6 + 8*8 = 100
     * 1*1+0*0+0*0 = 1
     */
    public boolean isHappy(int n) {
        while (n != 1 && n != 4) {
            int temp = 0;
            while (n != 0) {
                temp = (n % 10) * (n % 10) + temp;
                n = n / 10;
            }
            n = temp;
        }
        return n == 1;
    }

    public boolean isHappy2(int n) {
        List<Integer> list = new ArrayList<>();
        list.add(n);
        while (n != 1) {
            int temp = 0;
            while (n != 0) {
                temp = (n % 10) * (n % 10) + temp;
                n = n / 10;
            }
            n = temp;
            if (list.contains(n)) {
                break;
            } else {
                list.add(n);
            }
        }
        return n == 1;
    }


    public static void main(String[] args) {
        int num = 19;
        DataDemo dataDemo = new DataDemo();
        System.out.println(dataDemo.isHappy2(num));
    }
}
