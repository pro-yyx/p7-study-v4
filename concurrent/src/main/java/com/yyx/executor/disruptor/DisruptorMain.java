package com.yyx.executor.disruptor;

import com.lmax.disruptor.BusySpinWaitStrategy;
import com.lmax.disruptor.EventFactory;
import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.dsl.ProducerType;

import java.nio.ByteBuffer;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class DisruptorMain {

    public static void main(String[] args) {
        //1.创建一个可缓存的线程，提供线程来发送consumer的事件处理
        ExecutorService executorService=Executors.newCachedThreadPool();
        //2.创建工厂
        EventFactory<LongEvent> eventEventFactory = new LongEventFactory();
        //3.创建ringbuffer大小:必须是2的N次方，用于取模计算index
        int ringBufferSize = 1024 * 1024;
        //4.创建disruptor
        Disruptor<LongEvent> disruptor = new Disruptor<LongEvent>(eventEventFactory,
                ringBufferSize, executorService, ProducerType.SINGLE,new BusySpinWaitStrategy());
        //5、连接消费端方法
        disruptor.handleEventsWith(new LongEventHandler(1),new LongEventHandler(2));
        //6.启动消费者
        disruptor.start();
        //7.创建ringbuffer容器
        RingBuffer<LongEvent> ringBuffer = disruptor.getRingBuffer();
        //8.创建生产者
        LongEventProducer longEventProducer = new LongEventProducer(ringBuffer);
        //9.指定缓冲区大小
        ByteBuffer byteBuffer=ByteBuffer.allocate(8);
        for (int i = 0; i <= 100; i++) {
            byteBuffer.putLong(0, i);
            longEventProducer.onData(byteBuffer);
        }
        //10.关闭disruptor和executor
        disruptor.shutdown();
        executorService.shutdown();
    }
}
