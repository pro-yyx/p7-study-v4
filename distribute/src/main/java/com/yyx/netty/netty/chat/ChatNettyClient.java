package com.yyx.netty.netty.chat;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

import java.util.Scanner;

/**
 * @author yinyuxin
 * @description
 * @date 2021/9/1 18:23
 */
public class ChatNettyClient {

    public static void main(String[] args) throws InterruptedException {
        EventLoopGroup eventExecutors=new NioEventLoopGroup();
        try {
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.group(eventExecutors)
                    .channel(NioSocketChannel.class)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            socketChannel.pipeline().addLast( new StringDecoder());
                            socketChannel.pipeline().addLast( new StringEncoder());
                            socketChannel.pipeline().addLast(new ChatNettyClientHandler());
                        }
                    });
            ChannelFuture channelFuture = bootstrap.connect("127.0.0.1",9000).sync();
            Channel channel = channelFuture.channel();
            System.out.println("当前客户端地址:" + channel.localAddress());
            //客户端需要输入信息，创建一个扫描器
            Scanner scanner=new Scanner(System.in);
            while (scanner.hasNextLine()) {
                String s = scanner.nextLine();
                channel.writeAndFlush(s);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }finally {
            /*channelFuture.channel().close().sync();
            eventExecutors.shutdownGracefully();*/
        }

    }

}
