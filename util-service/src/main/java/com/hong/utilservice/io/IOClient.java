package com.hong.utilservice.io;

import java.net.Socket;
import java.util.Date;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author liang
 * @description
 * @date 2020/6/24 14:57
 */
public class IOClient {

    private static AtomicInteger atomicInteger = new AtomicInteger();

    public static void main(String[] args) {
        for (int i = 0; i < 5; i++) {
            //客户端连接服务端
            new Thread(() -> {
                try {
                    Socket socket = new Socket("localhost", 6666);
                    while (true) {
                        try {
                            int num = atomicInteger.incrementAndGet();
                            System.out.println("客户端发送消息:" + num);
                            socket.getOutputStream().write((new Date() + ": Hello World " + num).getBytes());
                            Thread.sleep(2000);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }).start();
        }
    }


}
