package com.yyx.netty.nio;

import java.io.BufferedReader;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.security.Key;
import java.util.Iterator;
import java.util.Set;

/**
 * @author yinyuxin
 * @description 多路复用器
 * @date 2021/8/20 17:47
 */
public class NioSelectorSocketServer {

    public static void main(String[] args) throws IOException {
        //创建NIO serversocketchannel
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.bind(new InetSocketAddress(8000));
        serverSocketChannel.configureBlocking(false);
        //打开selector处理channel,即创建epoll
        Selector selector = Selector.open();
        //把serversocketchannel注册到selector上，并且selector对客户端accept连接操作感兴趣
        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
        System.out.println("服务启动成功");
        while (true) {
            //阻塞等待需要处理的事件发生
            selector.select();
            //获取selector中注册的全部事件的selectionKey 实例
            Set<SelectionKey> selectionKeys = selector.selectedKeys();
            Iterator<SelectionKey> iterator = selectionKeys.iterator();
            //遍历selectionKey对事件进行处理
            while (iterator.hasNext()) {
                SelectionKey next = iterator.next();
                //如果是OP_ACCEPT事件，则进行连接获取和事件注册
                if (next.isAcceptable()) {
                    ServerSocketChannel channel = (ServerSocketChannel) next.channel();
                    SocketChannel accept = channel.accept();
                    accept.configureBlocking(false);
                    //如果需要给客户端发送数据还可注册写事件
                    accept.register(selector, SelectionKey.OP_READ);
                    System.out.println("客户端连接成功");
                } else if (next.isReadable()) {
                    //如果是OP_READ事件，则进行读取和打印
                    SocketChannel channel = (SocketChannel) next.channel();
                    ByteBuffer byteBuffer = ByteBuffer.allocate(128);
                    int read = channel.read(byteBuffer);
                    if (read > 0) {
                        System.out.println("接收到消息:" + new String(byteBuffer.array()));
                    } else if (read == -1) {
                        iterator.remove();
                        System.out.println("客户端断开连接");
                    }
                }
                //从事件集合中删除本次处理的key，以防下次select重复处理
                iterator.remove();
            }
        }
    }
}
