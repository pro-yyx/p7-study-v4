package com.yyx.netty.netty.reconnect;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * @author yinyuxin
 * @description
 * @date 2021/9/3 14:07
 */
public class NettyReconnectClientHandler extends SimpleChannelInboundHandler<String> {

    private NettyReconnectClient nettyReconnectClient;


    public NettyReconnectClientHandler(NettyReconnectClient nettyReconnectClient) {
        this.nettyReconnectClient = nettyReconnectClient;
    }

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, String s) throws Exception {
        System.out.println("[client] receive message:" + s);
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("[client] 断开连接");
        nettyReconnectClient.connect();
    }
}
