package com.yyx.netty.netty.chat;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioChannelOption;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.util.internal.StringUtil;
import org.apache.commons.lang3.StringUtils;

import javax.swing.*;

/**
 * @author yinyuxin
 * @description
 * @date 2021/9/1 17:50
 */
public class ChatNettyServer {

    public static void main(String[] args) throws InterruptedException {
        EventLoopGroup bossEventLoopGroup=new NioEventLoopGroup(1);
        EventLoopGroup workEventLoopGroup=new NioEventLoopGroup(2);
        ServerBootstrap serverBootstrap = new ServerBootstrap();
        serverBootstrap.group(bossEventLoopGroup, workEventLoopGroup)
                .channel( NioServerSocketChannel.class)
                .option(ChannelOption.SO_BACKLOG,1024)
                .childHandler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel channel) throws Exception {
                        channel.pipeline().addLast("decoder", new StringDecoder());
                        channel.pipeline().addLast("encoder", new StringEncoder());
                        channel.pipeline().addLast(new ChatNettyServerHandler());
                    }
                });
        System.out.println("聊天室server启动了。。。");
        ChannelFuture sync = serverBootstrap.bind(9000).sync();
        sync.channel().closeFuture().sync();
        bossEventLoopGroup.shutdownGracefully();
        workEventLoopGroup.shutdownGracefully();
    }
}
