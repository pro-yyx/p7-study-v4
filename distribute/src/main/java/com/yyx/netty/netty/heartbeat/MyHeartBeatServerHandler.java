package com.yyx.netty.netty.heartbeat;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;

/**
 * @author yinyuxin
 * @description
 * @date 2021/9/2 17:10
 */
public class MyHeartBeatServerHandler extends SimpleChannelInboundHandler<String> {

    int readIdleTimes = 0;

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, String s) throws Exception {
        System.out.println("[server] received message:" + s);
        if ("Heartbeat Packet".equals(s)) {
            channelHandlerContext.channel().writeAndFlush("ok");
        } else {
            System.out.println("不是心跳包，转为其他信息处理");
        }
    }

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        IdleStateEvent idleStateEvent = (IdleStateEvent) evt;
        IdleState state = idleStateEvent.state();
        String eventStatus = null;
        switch (state) {
            case ALL_IDLE:
                eventStatus = "读写空闲";
                break;
            case READER_IDLE:
                eventStatus = "读空闲";
                readIdleTimes++;
                break;
            case WRITER_IDLE:
                eventStatus = "写空闲";
                break;
            default:
                break;
        }
        System.out.println(ctx.channel().remoteAddress()+" 超时事件："+eventStatus);
        if (readIdleTimes > 3) {
            System.out.println("读空闲次数超过三次，关闭连接，释放更多资源");
            ctx.channel().writeAndFlush("idle close");
            ctx.channel().close();
        }
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("===="+ctx.channel().remoteAddress()+" is active ====");
    }
}
