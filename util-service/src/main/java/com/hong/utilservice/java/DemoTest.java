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

    private int[] arr = {1, 2, 3};

    private void test1(int a, int b) {
        a = 5;
        b = 10;
    }

    private void test2(Integer aa, Integer bb) {
        Integer temp = aa;
        aa = bb;
        bb = temp;
    }

    private void test3(int[] arr) {
        arr[0] = 0;
    }

    public void print() {
        System.out.println("a:" + a + ",b:" + b);
        test1(a,b);
        System.out.println("a:" + a + ",b:" + b);

        System.out.println("============");
        System.out.println("aa:" + aa + ",bb:" + bb);
        test2(aa, bb);
        System.out.println("aa:" + aa + ",bb:" + bb);

        System.out.println("============");
        test3(arr);
        System.out.println(arr[0]);
    }




    public static int demoTryCatch() {
        int x;
        try {
            x = 1;
            return x;
        } catch (Exception e) {
            x = 2;
            return x;
        } finally {
            x = 3;
            return x;
        }
    }

    private static void test4(){
        int a = 1000;
        int b = 1000;
        System.out.println(a==b);
    }

    public static void main(String[] args) {
        DemoTest.test4();
    }
}
