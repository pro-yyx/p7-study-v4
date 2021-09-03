package com.yyx.netty.netty.chat;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.EventExecutor;
import io.netty.util.concurrent.GlobalEventExecutor;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author yinyuxin
 * @description
 * @date 2021/9/1 18:03
 */
public class ChatNettyServerHandler extends SimpleChannelInboundHandler<String> {

    /**
     * GlobalEventExecutor.INSTANCE 是全局的事件执行器，是一个单例
     */
    private static ChannelGroup baseChannelGroup=new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);

    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");


    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();
        //将该客户加入聊天室的信息推送给其他在线的客户端
        //该方法会将ChannelGroup中所有的channel进行遍历，并发送消息
        baseChannelGroup.writeAndFlush("客户端:" + channel.remoteAddress() + " 上线了:" + simpleDateFormat.format(new Date())+"\n");
        //将当前channel装入 baseChannelGroup
        baseChannelGroup.add(channel);
        System.out.println(channel.remoteAddress()+"上线了");
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        //客户端处于不活动状态，提示其他用户离线了
        Channel channel = ctx.channel();
        baseChannelGroup.writeAndFlush("客户端:" + channel.remoteAddress() + " 下线了\n");
        /*baseChannelGroup.remove(channel);*/
        System.out.println(channel.remoteAddress()+"下线了"+"channel group size:"+baseChannelGroup.size());
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();

    }

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, String s) throws Exception {
        Channel channel = channelHandlerContext.channel();
        baseChannelGroup.forEach(ch -> {
            if (channel != ch) {
                //不是当前channel，转发消息
                ch.writeAndFlush("客户端：" + channel.remoteAddress() + "发送了消息：" + s + "\n");
            } else {
                //回显自己发送的消息给自己
                ch.writeAndFlush("自己：发送了消息：" + s + "\n");
            }
        });
    }
}
