package com.yyx.netty.netty.base;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.CharsetUtil;

/**
 * @author yinyuxin
 * @description 自定义Handler需要继承netty规定好的某个HandlerAdapter(规范)
 * @date 2021/8/26 16:23
 */
public class NettyServerhandler extends ChannelInboundHandlerAdapter {

    /**
     * @Description 读取客户端发送的数据
     * @author yinyuxin
     * @date 2021/8/26
     * @param  * @param ctx 上下文对象，含有通道channel，管道pipeline
     * @param msg  客户端发送的数据
     * @return void
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        System.out.println("服务器读取线程"+Thread.currentThread().getName());
        //将msg转成一个ByteBuf，类似于NIO的ByteBuffer
        ByteBuf buf = (ByteBuf) msg;
        System.out.println("客户端发送的消息是："+buf.toString(CharsetUtil.UTF_8));
    }

    /**
     * @Description 数据读取完毕后的处理方法
     * @author yinyuxin
     * @date 2021/8/26
     * @param  * @param ctx
     * @return void
     */
    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ByteBuf buf= Unpooled.copiedBuffer("HelloClient",CharsetUtil.UTF_8);
        ctx.writeAndFlush(buf);
    }

    /**
     * @Description 处理异常，一般是需要关闭通道
     * @author yinyuxin
     * @date 2021/8/26
     * @param  * @param ctx
     * @param cause
     * @return void
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
    }
}
