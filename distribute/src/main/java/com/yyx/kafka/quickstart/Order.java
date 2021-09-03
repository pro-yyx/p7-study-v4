package com.yyx.kafka.quickstart;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author yinyuxin
 * @description
 * @date 2021/6/18 0:31
 */
@Data
@AllArgsConstructor
public class Order {

    private Integer orderId;

    private Integer productId;

    private Integer productNum;

    private Double orderAmount;

}
