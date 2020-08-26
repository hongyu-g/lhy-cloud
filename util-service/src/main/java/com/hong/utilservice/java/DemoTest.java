package com.hong.utilservice.java;

import java.io.*;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author liang
 * @description
 * @date 2020/7/6 15:57
 */
public class DemoTest implements Serializable {

    /**
     * 序列化ID
     * java的序列化机制是通过在运行时判断类的serialVersionUID来验证版本一致性的
     */
    private static final long serialVersionUID = -1L;


    private int a = 10;
    private int b = 5;

    private Integer aa = 10;
    private Integer bb = 5;

    private int[] arr = {1, 2, 3};

    private void test1(int a, int b) {
        a = 5;
        b = 10;
    }

    public void setA(int a) {
        this.a = a;
    }

    public int getA() {
        return a;
    }

    public void setB(int b) {
        this.b = b;
    }

    public int getB() {
        return b;
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
        test1(a, b);
        System.out.println("a:" + a + ",b:" + b);

        System.out.println("============");
        System.out.println("aa:" + aa + ",bb:" + bb);
        test2(aa, bb);
        System.out.println("aa:" + aa + ",bb:" + bb);

        System.out.println("============");
        test3(arr);
        System.out.println(arr[0]);
    }


    public int demoTryCatch() {
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

    /**
     * double 精度问题
     */
    public void test4() {
        double num = 0.05;
        double num2 = 0.01;
        double result = num + num2;
        System.out.println(result);

        result = num * 100 + num2 * 100;
        System.out.println(result / 100);

        //使用参数为String类型的构造方法
        System.out.println(new BigDecimal(num));
        BigDecimal b1 = new BigDecimal(Double.toString(num));
        BigDecimal b2 = new BigDecimal(Double.toString(num2));
        result = b1.add(b2).doubleValue();
        System.out.println(result);
    }

    public void copyDemo() {
        int[] arr = {1, 2, 3, 4, 5};
        int[] newArr = new int[10];
        int index = 0;
        for (int i = 0; i < arr.length; i++) {
            newArr[index++] = arr[i];
        }
        //效率高，内存复制
        System.arraycopy(arr, 0, newArr, 0, arr.length);
        for (int num : newArr) {
            System.out.println(num);
        }
    }

    /**
     * 边循环边删除
     */
    public void arrayListForEach() {
        List<Integer> list = new ArrayList<>();
        list.add(1);
        list.add(2);
        list.add(2);
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).equals(2)) {
                list.remove(i);
                i--;
            }
        }
        System.out.println(list);
    }

    public void arrList() {
        Integer[] arr = {1, 2, 3};
        //Arrays 内部类  java.util.Arrays.ArrayList
        List<Integer> list = Arrays.asList(arr);
        //传入了引用，值传递
        arr[0] = 4;
        System.out.println(list);
    }


    public void serializeDemo() {
        try {
            OutputStream outputStream = new ObjectOutputStream(new FileOutputStream("D:\\a.txt"));
            DemoTest demoTest = new DemoTest();
            demoTest.setA(10);
            ((ObjectOutputStream) outputStream).writeObject(demoTest);
            System.out.println("序列化成功");
            outputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void deSerializeDemo() {
        try {
            InputStream inputStream = new ObjectInputStream(new FileInputStream("D:\\a.txt"));
            DemoTest demoTest = new DemoTest();
            demoTest.setA(10);
            ((ObjectInputStream) inputStream).readObject();
            System.out.println("反序列化成功");
            inputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void dateDemo() {
        try {
            //非线程安全
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            System.out.println(sdf.parse("2020-08-26 15:00:24"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    public static void main(String[] args) {
        DemoTest demo = new DemoTest();
    }
}
