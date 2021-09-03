package com.yyx.redis;

import lombok.extern.slf4j.Slf4j;
import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisPoolConfig;

import java.util.HashSet;
import java.util.Set;

@Slf4j
public class Redis03_ConnectCluster {

    public static void main(String[] args) {
        JedisPoolConfig jedisPoolConfig=new JedisPoolConfig();
        jedisPoolConfig.setMaxTotal(20);
        jedisPoolConfig.setMaxIdle(10);
        jedisPoolConfig.setMinIdle(5);
        Set<HostAndPort> jedisClusterNode = new HashSet<>();
        jedisClusterNode.add(new HostAndPort("192.168.89.4", 8001));
        jedisClusterNode.add(new HostAndPort("192.168.89.4", 8004));
        jedisClusterNode.add(new HostAndPort("192.168.89.5", 8002));
        jedisClusterNode.add(new HostAndPort("192.168.89.5", 8003));
        jedisClusterNode.add(new HostAndPort("192.168.89.5", 8005));
        jedisClusterNode.add(new HostAndPort("192.168.89.5", 8006));
        JedisCluster jedisCluster = null;
        try {
            //connectionTimeout值的是一个url的连接等待时间
            //soTimeout:连接上一个url，获取response的返回等待时间
            jedisCluster=new JedisCluster(jedisClusterNode, 5000, 4000,10,
                    "root123",jedisPoolConfig);
            log.info(jedisCluster.set("cluster","yyx"));
            log.info(jedisCluster.get("cluster"));
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (jedisCluster != null) {
                jedisCluster.close();
            }
        }
    }
}
