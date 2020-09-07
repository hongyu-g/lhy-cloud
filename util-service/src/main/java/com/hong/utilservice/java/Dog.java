package com.hong.utilservice.java;

public class Dog extends Animal {

    static {
        System.out.println("static Dog");
    }


    public Dog() {
        System.out.println("构造方法 Dog");
    }

    @Override
    public void eat() {
        System.out.println("dog eat");
    }

    public static void sleep() {
        System.out.println("dog sleep");
    }

    private void hit() {
        System.out.println("dog hit");
    }
}
