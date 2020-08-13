package com.hong.authservice.aop;

import com.hong.authservice.service.HelloService;
import com.hong.authservice.service.impl.HelloServiceImpl;
import org.aopalliance.aop.Advice;
import org.springframework.aop.Advisor;
import org.springframework.aop.aspectj.annotation.AspectJProxyFactory;
import org.springframework.aop.framework.ProxyFactory;
import org.springframework.aop.support.DefaultPointcutAdvisor;

import java.lang.reflect.Method;

/**
 * @author liang
 * @description
 * @date 2020/7/3 18:09
 */
public class DemoTest {

    /**
     * JDK代理
     */
    public static void jdkProxyTest() {
        //System.getProperties().setProperty("sun.misc.ProxyGenerator.saveGeneratedFiles", "true");
        HelloService hello = new HelloServiceImpl();
        HelloService proxy = (HelloService) new HelloJDKProxy(hello).getJDKProxy();
        proxy.sayHello();
    }

    /**
     * cgLib代理
     */
    public static void cgLibProxyTest() {
        //System.setProperty(DebuggingClassWriter.DEBUG_LOCATION_PROPERTY, "D:\\spring-cloud-demo");
        HelloService hello = new HelloServiceImpl();
        HelloService proxy = (HelloService) new HelloCGLibProxy().getCGLibProxy(hello);
        proxy.sayHello();
    }

    /**
     * Spring AOP
     */
    public static void springAOPTest() {
        HelloServiceImpl hello = new HelloServiceImpl();
        ProxyFactory proxyFactory = new ProxyFactory();
        //设置目标类
        proxyFactory.setTarget(hello);

        //设置增强逻辑
        Advice advice = new TimeHandler.AdviceTest2();
        //一个Advisor代表一个已经跟指定切点绑定的通知
        Advisor advisor = new DefaultPointcutAdvisor(new DemoPointCut(), advice);
        //添加一个指定切点的通知
        proxyFactory.addAdvisor(advisor);
        HelloServiceImpl helloProxy = (HelloServiceImpl) proxyFactory.getProxy();
        System.err.println(helloProxy.hello());
    }

    /**
     * AspectJ
     */
    public static void aspectJTest() {
        HelloServiceImpl hello = new HelloServiceImpl();
        AspectJProxyFactory proxyFactory = new AspectJProxyFactory();
        proxyFactory.setTarget(hello);
        proxyFactory.addAspect(AdviceHandler.class);
        HelloServiceImpl helloProxy = proxyFactory.getProxy();
        helloProxy.sayHello();
    }


    @TestAnnotation
    public void hello() {
        System.out.println("test");
    }

    /**
     * 测试注解
     */
    public static void test() throws Exception {
        DemoTest demo = new DemoTest();
        Method method = DemoTest.class.getMethod("hello");
        TestAnnotation testAnnotation = method.getAnnotation(TestAnnotation.class);
        if (testAnnotation != null) {
            System.out.println(testAnnotation.paramValue());
        }
        method.invoke(demo);
    }

    public static void main(String[] args) throws Exception {
        DemoTest.springAOPTest();
    }
}
