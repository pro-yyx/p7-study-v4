package com.yyx.zookeeper.curator;

import org.apache.curator.framework.CuratorFramework;

public class CuratorStandaloneBase {
    private static final String ZOOKEEPER_SERVER = "192.168.89.6:2181";
    private static final Integer SEESSION_TIMEOUT = 30 * 1000;
    private static final Integer CONNECTIONTIMEOUT = 5 * 1000;
    private CuratorFramework curatorFramework;

    public static void main(String[] args) {

    }

    private static void init() {
        //CuratorFramework= CuratorFrameworkFactory.builder().connectString()
    }
}
