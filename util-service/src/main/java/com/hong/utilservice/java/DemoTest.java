package com.hong.utilservice.java;

/**
 * @author liang
 * @description
 * @date 2020/7/6 15:57
 */
public class DemoTest {

    private Animal animal;

    /**
     * 动态绑定：运行时确定调用类
     * 静态绑定：编译时确定调用类（调用静态方法、final方法、构造函数）
     */

    public DemoTest(Animal animal) {
        this.animal = animal;
    }


    public void test() {
        animal.eat();
    }

    public static void main(String[] args) {
        new DemoTest(new Panda()).test();
    }
}
