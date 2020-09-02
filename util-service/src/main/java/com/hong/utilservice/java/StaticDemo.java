package com.hong.utilservice.java;

/**
 * @author liang
 * @description
 * @date 2020/8/26 16:20
 */
public class StaticDemo {

    /**
     * 随着类加载初始化，初始化一次，静态变量与类绑定
     */
    private static int num = 1;


    private final int count = 10;
    private final User user = new User();

    /**
     * 类名调用，生命周期随和类相同
     * 与类变量不同，方法（静态方法与实例方法）在内存中只有一份，无论该类有多少个实例，都共用一个方法。
     */
    public static void demo() {
        System.out.println("test");
    }

    public static void main(String[] args) {
        User user = new StaticDemo().user;
        user.setId(10);
        System.out.println(user);
        User user2 = new StaticDemo().user;
        System.out.println(user2);
    }
}
