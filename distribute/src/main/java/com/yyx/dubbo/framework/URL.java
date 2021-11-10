package com.yyx.dubbo.framework;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author yinyuxin
 * @description 用于手动实现服务集群
 * @date 2021/9/15 18:18
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class URL implements Serializable {

    private String hostName;

    private Integer port;


}
