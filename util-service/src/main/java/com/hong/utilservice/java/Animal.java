package com.hong.utilservice.java;

/**
 * @author liang
 * @description 类型转换
 * @date 2020/9/1 17:02
 */
public class Animal {

    public void eat() {
        System.out.println("animal eat");
    }

    public void test() {
        //调用的子类的
        eat();
    }

    public static void main(String[] args) {
        //Animal animal = new Animal(); error
        Animal animal = new Dog();
        Dog dog = (Dog) animal;
        dog.eat();

        dog = new Dog();
        animal = (Animal) dog;
        animal.eat();
        animal.test();
    }

}

class Dog extends Animal {

    @Override
    public void eat() {
        System.out.println("dog eat");
    }
}
