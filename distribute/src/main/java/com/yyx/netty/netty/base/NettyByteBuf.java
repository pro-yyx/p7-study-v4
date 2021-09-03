package com.yyx.netty.netty.base;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.util.CharsetUtil;

/**
 * @author yinyuxin
 * @description
 * @date 2021/8/30 18:18
 */
public class NettyByteBuf {

    public static void main(String[] args) {
        //创建byteBuffer对象，该对象内部包含一个字节数组byte[10]
        //通过readIndex和writeIndex和capacity，将buffer分成三个区域
        //已经读取的区域:[0,readerIndex]
        //可读取的区域:[readIndex,writeIndex]
        //可写的区域:[writeIndex,capacity]
        ByteBuf buf = Unpooled.buffer(10);
        System.out.println("byteBuf=" + buf);
        for (int i = 0; i < 8; i++) {
            buf.writeByte(i);
        }
        System.out.println("byteBuf=" + buf);
        for (int i = 0; i < 5; i++) {
            System.out.println(buf.getByte(i));
        }
        System.out.println("byteBuf=" + buf);
        for (int i = 0; i < 5; i++) {
            System.out.println(buf.readByte());
        }
        System.out.println("byteBuf=" + buf);

        //用unpooled工具创建ByteBuf
        ByteBuf byteBuf = Unpooled.copiedBuffer("hello,yyx", CharsetUtil.UTF_8);
        //使用相关的方法
        if (byteBuf.hasArray()) {
            byte[] array = byteBuf.array();
            //将content 转成字符串
            System.out.println(new String(array, CharsetUtil.UTF_8));
            System.out.println("byteBuf=" + byteBuf);
            System.out.println(byteBuf.readerIndex());
            System.out.println(byteBuf.writerIndex());
            System.out.println(byteBuf.capacity());
            //获取数组0这个位置的字符h的ascii码，h=104
            System.out.println(byteBuf.getByte(0));
            //可读的字节数
            int i = byteBuf.readableBytes();
            System.out.println("readableBytes length="+i);
            //使用for循环取出各个字节
            for (int j = 0; j < i; j++) {
                System.out.println((char) byteBuf.getByte(j));
            }
        }
    }
}
