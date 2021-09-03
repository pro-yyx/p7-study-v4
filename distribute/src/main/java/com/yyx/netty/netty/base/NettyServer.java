package com.yyx.netty.netty.base;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.string.StringEncoder;

/**
 * @author yinyuxin
 * @description
 * @date 2021/8/26 15:23
 */
public class NettyServer {

    public static void main(String[] args) throws InterruptedException {
       //创建两个线程组 bossGroup 和workerGroup，含有的子线程NioEventLoop的个数默认为cpu核数的两倍
        //bossGroup 只是处理连接请求，真正的和客户端业务处理是交给workerGroup。
        EventLoopGroup bossGroup=new NioEventLoopGroup(1);
        EventLoopGroup workerGroup=new NioEventLoopGroup(1);
        //创建服务器端的启动对象
        ServerBootstrap bootstrap = new ServerBootstrap();
        //使用链式编程来配置参数
        bootstrap.group(bossGroup, workerGroup)
                //使用NioServerSocketChannel作为服务器的通道实现
                .channel(NioServerSocketChannel.class)
                //初始化服务器连接队列大小，服务端处理客户端连接请求是顺序处理的，所以同一时间只能处理一个客户端连接。
                //多个客户端同时来得时候，服务端将不能处理的客户端连接请求去放在队列中等待处理
                .option(ChannelOption.SO_BACKLOG,1024)
                .childHandler(new ChannelInitializer<SocketChannel>() {
                    //对wokerGroup的socketChannel设置处理器
                    @Override
                    protected void initChannel(SocketChannel socketChannel) throws Exception {
                        socketChannel.pipeline().addLast(new NettyServerhandler());
                    }
                });
        System.out.println("netty server start....");
        //绑定一个端口并且同步，生成了一个ChannelFuture异步对象，通过isDone()等方法可以判断异步事件的执行情况
        //启动服务器（并绑定端口），bind是异步操作，sync方法是等待异步操作执行完毕。
        ChannelFuture channelFuture = bootstrap.bind(9000).sync();
        //给channelFuture注册监听器，监听我们关心的事件
        channelFuture.addListener((ChannelFutureListener) channelFuture1 -> {
            if (channelFuture1.isSuccess()) {
                System.out.println("监听端口9000成功");
            } else {
                System.out.println("监听端口9000失败");
            }
        });
        //等待服务端监听端口关闭，closeFuture是异步操作
        //通过sync方法同步等待通道关闭，这里会阻塞等待通道关闭完成，内部调用的是Object的wait()方法
        channelFuture.channel().closeFuture().sync();
        bossGroup.shutdownGracefully();
        workerGroup.shutdownGracefully();
    }
}
