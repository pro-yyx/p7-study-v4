package com.yyx.executor.disruptor;

import lombok.Data;

/**
 * 定义事件event 通过disruptor 进行交换的数据类型
 */
@Data
public class LongEvent {

    private Long value;

}
