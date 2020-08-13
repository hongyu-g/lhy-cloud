package com.hong.utilservice.io;

import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @author liang
 * @description
 * @date 2020/6/24 15:02
 */
public class IOServer {

    public static void main(String[] args) throws Exception {
        //服务端处理客户端连接请求
        ServerSocket serverSocket = new ServerSocket(6666);

        //接收客户端请求后，为每个客户端创建一个线程进行链路处理
        new Thread(() -> {
            while (true) {
                try {
                    //阻塞方法获取新的连接
                    Socket socket = serverSocket.accept();
                    //每一个新的连接都创建一个线程，负责读取数据
                    new Thread(() -> {
                        try {
                            int len;
                            byte[] data = new byte[1024];
                            InputStream inputStream = socket.getInputStream();
                            while ((len = inputStream.read(data)) != -1) {
                                System.out.println("服务端接收请求：" + new String(data, 0, len));
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }).start();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

        }).start();
    }
}
