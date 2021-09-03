package com.yyx.executor.disruptor;

import com.lmax.disruptor.EventHandler;
import lombok.extern.slf4j.Slf4j;

/**
 * 事件消费者
 */
@Slf4j
public class LongEventHandler implements EventHandler<LongEvent> {

    private long serial = 0;

    public LongEventHandler(long serial) {
        this.serial = serial;
    }

    public LongEventHandler() {
    }

    @Override
    public void onEvent(LongEvent longEvent, long l, boolean b) throws Exception {
        log.info("消费者-{}:{}",this.serial,longEvent.getValue());
    }
}
