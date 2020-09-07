package com.hong.utilservice.java;

/**
 * @author liang
 * @description 类型转换
 * @date 2020/9/1 17:02
 */
public class Animal {

    static {
        System.out.println("static Animal");
    }

    public static int value = 1;

    public Animal() {
        System.out.println("构造方法 Animal");
    }

    public void eat() {
        System.out.println("animal eat");
    }

    /**
     * 静态绑定
     */
    public static void sleep() {
        System.out.println("animal sleep");
    }

    /**
     * 私有方法
     */
    private void hit() {
        System.out.println("animal hit");
    }

    /**
     * final修饰的
     */
    public final void hello() {
        System.out.println("animal hello");
    }


    public static void main(String[] args) {
        Animal animal = new Dog();
        animal.sleep();
        animal.hit();
        animal.hello();
    }

}
