package com.hong.utilservice.java;

/**
 * @author liang
 * @description
 * @date 2020/7/6 15:57
 */
public class DemoTest {


    private int a = 10;
    private int b = 5;

    private Integer aa = 10;
    private Integer bb = 5;

    private void test1(int a, int b) {
        a = 5;
        b = 10;
    }

    private void test2(Integer aa, Integer bb) {
        Integer temp = aa;
        aa = bb;
        bb = temp;
    }


    public void print() {
        System.out.println("a:" + a + ",b:" + b);
        test1(a, b);
        System.out.println("a:" + a + ",b:" + b);

        System.out.println("============");
        System.out.println("aa:" + aa + ",bb:" + bb);
        test2(aa, bb);
        System.out.println("aa:" + aa + ",bb:" + bb);
    }

}
