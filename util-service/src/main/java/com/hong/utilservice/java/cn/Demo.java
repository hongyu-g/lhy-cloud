package com.hong.utilservice.java.cn;

import com.hong.utilservice.java.DemoTest;

public class Demo extends DemoTest{

    public void demo() {
        DemoTest demoTest = new DemoTest();
        Demo demo = new Demo();
        int count = demo.count;
        //demoTest.count;
        System.out.println(count);
    }
}
