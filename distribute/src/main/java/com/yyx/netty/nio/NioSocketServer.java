package com.yyx.netty.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * @author yinyuxin
 * @description
 * @date 2021/8/20 16:57
 */
public class NioSocketServer {

    public static void main(String[] args) throws IOException {
        //创建NIO ServerSocketChannel,与BIO的serversocket类似
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.bind(new InetSocketAddress(8000));
        //设置serverSocketChannel为非阻塞
        serverSocketChannel.configureBlocking(false);
        System.out.println("服务启动成功");
        //保存客户端连接
        List<SocketChannel> channelList = new ArrayList<>();
        while (true) {
            //非阻塞模式accept方法不会阻塞
            //NIO的非阻塞是有操作系统内部实现的，底层调用了linux内核的accept函数。
            SocketChannel accept = serverSocketChannel.accept();
            if (accept != null) {
                System.out.println("连接成功");
                //设置socketChannel为非阻塞
                accept.configureBlocking(false);
                //保存客户端连接在list中
                channelList.add(accept);
            }
            //遍历连接进行数据读取
            Iterator<SocketChannel> iterator = channelList.iterator();
            while (iterator.hasNext()) {
                SocketChannel socketChannel = iterator.next();
                ByteBuffer byteBuffer = ByteBuffer.allocate(128);
                //非阻塞模式read方法不会阻塞
                int read = socketChannel.read(byteBuffer);
                //如果有数据，打印出来
                if (read > 0) {
                    System.out.println("接收到消息:" + new String(byteBuffer.array()));
                } else if (read == -1) {
                    iterator.remove();
                    System.out.println("客户端断开连接");
                }
            }
        }
    }
}
