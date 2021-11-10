package com.yyx.netty.netty.reconnect;

import com.yyx.netty.netty.base.NettyClientHandler;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

import java.util.concurrent.TimeUnit;

/**
 * @author yinyuxin
 * @description
 * @date 2021/8/26 16:36
 */
public class NettyReconnectClient {

    private String host;

    private Integer port;

    private Bootstrap bootstrap;

    private EventLoopGroup group;


    public static void main(String[] args) throws Exception {
        NettyReconnectClient nettyReconnectClient = new NettyReconnectClient("127.0.0.1", 9000);
        nettyReconnectClient.connect();
    }

    public NettyReconnectClient(String host, Integer port) {
        this.host = host;
        this.port = port;
        init();
    }

    public void init() {
        group = new NioEventLoopGroup();
        bootstrap = new Bootstrap();
        bootstrap.group(group).channel(NioSocketChannel.class)
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel socketChannel) throws Exception {
                        socketChannel.pipeline().addLast(new StringDecoder());
                        socketChannel.pipeline().addLast(new StringEncoder());
                        socketChannel.pipeline().addLast(new NettyReconnectClientHandler(NettyReconnectClient.this));
                    }
                });
    }

    public void connect() throws InterruptedException {

        System.out.println("netty client start connect ....");
        ChannelFuture sync = bootstrap.connect(host, port);
        sync.addListener((ChannelFutureListener) channelFuture1 -> {
            if (!channelFuture1.isSuccess()) {
                //另起线程执行重连请求
                channelFuture1.channel().eventLoop().schedule(()->{
                    System.out.println("重新连接服务器");
                    try {
                        connect();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                },1, TimeUnit.SECONDS);
            }
        });
        sync.channel().closeFuture().sync();
    }
}
