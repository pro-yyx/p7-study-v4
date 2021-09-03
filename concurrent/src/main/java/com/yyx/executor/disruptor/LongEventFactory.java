package com.yyx.executor.disruptor;

import com.lmax.disruptor.EventFactory;

/**
 * 定义事件工厂，创建event
 */
public class LongEventFactory implements EventFactory<LongEvent> {
    @Override
    public LongEvent newInstance() {
        return new LongEvent();
    }
}
