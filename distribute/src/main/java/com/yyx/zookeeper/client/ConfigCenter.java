package com.yyx.zookeeper.client;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.zookeeper.*;
import org.apache.zookeeper.data.Stat;
import org.springframework.util.Base64Utils;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Base64;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

@Slf4j
public class ConfigCenter {

    private final static String CONNECT_SERVER = "192.168.89.6:2181";

    private final static Integer SESSION_TIMEOUT = 30*1000;

    private static ZooKeeper zooKeeper = null;

    private static CountDownLatch countDownLatch=new CountDownLatch(1);

    public static void main(String[] args) throws IOException, InterruptedException, KeeperException {
        ObjectMapper objectMapper = new ObjectMapper();
        Watcher watcher= watchedEvent -> {
            if (Watcher.Event.EventType.None.equals(watchedEvent.getType()) &&
                    Watcher.Event.KeeperState.SyncConnected.equals(watchedEvent.getState())) {
                log.info("zookeeper server连接成功");
                countDownLatch.countDown();
            }
        };
        zooKeeper=new ZooKeeper(CONNECT_SERVER, SESSION_TIMEOUT, watcher);
        //初始化zookeeper时，会创建两个守护线程（main线程结束，守护线程也会跟着结束，）
        countDownLatch.await();

        MyConfig myConfig = new MyConfig();
        myConfig.setName("yyx");
        myConfig.setAge(28);
        myConfig.setRateMoney(new BigDecimal(12345678));
        byte[] value = JSONObject.toJSONBytes(myConfig);
        zooKeeper.create("/myconfig", value, ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL);
        Watcher configChangeWatcher = new Watcher() {
            @Override
            public void process(WatchedEvent watchedEvent) {
                if (Watcher.Event.EventType.NodeDataChanged.equals(watchedEvent.getType())
                        && ("/myconfig").equals(watchedEvent.getPath())) {
                    try {
                        byte[] data = zooKeeper.getData("/myconfig",this, null);
                        MyConfig myConfig1 = objectMapper.readValue(new String(data), MyConfig.class);
                        log.info("监听到配置改变，新配置：{}", JSONArray.toJSONString(myConfig1));
                    } catch (KeeperException e) {
                        e.printStackTrace();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } catch (JsonMappingException e) {
                        e.printStackTrace();
                    } catch (JsonProcessingException e) {
                        e.printStackTrace();
                    }
                }
            }
        };
        byte[] data = zooKeeper.getData("/myconfig", configChangeWatcher, null);
        MyConfig myConfig0= objectMapper.readValue(new String(data), MyConfig.class);
        log.info("原始配置：{}", JSONArray.toJSONString(myConfig0));
        TimeUnit.SECONDS.sleep(600);
    }
}
