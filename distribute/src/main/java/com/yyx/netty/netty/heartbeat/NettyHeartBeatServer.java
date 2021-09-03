package com.yyx.netty.netty.heartbeat;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.handler.timeout.IdleStateHandler;

import java.util.concurrent.TimeUnit;

/**
 * @author yinyuxin
 * @description
 * @date 2021/9/2 17:00
 */
public class NettyHeartBeatServer {

    public static void main(String[] args) throws InterruptedException {
        EventLoopGroup bossLoopGroup=new NioEventLoopGroup(1);
        EventLoopGroup workLoopGroup=new NioEventLoopGroup(1);
        ServerBootstrap bootstrap=new ServerBootstrap().group(bossLoopGroup, workLoopGroup);
        bootstrap.channel(NioServerSocketChannel.class)
                .option(ChannelOption.SO_BACKLOG, 1024)
                .childHandler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel socketChannel) throws Exception {
                        ChannelPipeline pipeline = socketChannel.pipeline();
                        pipeline.addLast(new StringDecoder());
                        pipeline.addLast(new StringEncoder());
                        //第一个参数指定超过3秒还没收到客户端的连接，会触发IdleStateEvent事件并且交给下一个handler处理，
                        //下一个handler必须实现userEventTriggerd方法处理对应事件
                        pipeline.addLast(new IdleStateHandler(3, 0, 0, TimeUnit.SECONDS));
                        pipeline.addLast(new MyHeartBeatServerHandler());
                    }
                });
        System.out.println("netty server started....");
        ChannelFuture channelFuture = bootstrap.bind(9000).sync();
        channelFuture.channel().closeFuture().sync();
        workLoopGroup.shutdownGracefully();
        bossLoopGroup.shutdownGracefully();
    }
}
