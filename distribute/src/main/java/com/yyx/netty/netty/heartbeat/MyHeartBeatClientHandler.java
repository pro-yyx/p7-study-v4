package com.yyx.netty.netty.heartbeat;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.apache.commons.lang3.StringUtils;

/**
 * @author yinyuxin
 * @description
 * @date 2021/9/2 17:25
 */
public class MyHeartBeatClientHandler extends SimpleChannelInboundHandler<String> {
    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, String s) throws Exception {
        System.out.println("client received:" + s);
        if (StringUtils.equals("idle close", s)) {
            System.out.println("服务端已关闭连接，客户端同步关闭");
            channelHandlerContext.channel().close();
        }
    }
}
