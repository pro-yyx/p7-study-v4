package com.yyx.dubbo.framework;

import lombok.Data;

import java.io.Serializable;

/**
 * @author yinyuxin
 * @description
 * @date 2021/9/14 17:40
 */
@Data
public class Invocation implements Serializable {

    private String methodName;

    private String intefaceName;

    private Class[] paramTypes;

    private Object[] params;

}
