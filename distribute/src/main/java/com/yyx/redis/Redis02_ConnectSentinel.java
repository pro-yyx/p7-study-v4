package com.yyx.redis;

import lombok.extern.slf4j.Slf4j;
import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.JedisSentinelPool;

import java.util.HashSet;
import java.util.Set;

@Slf4j
public class Redis02_ConnectSentinel {
    public static void main(String[] args) {
        JedisPoolConfig jedisPoolConfig=new JedisPoolConfig();
        jedisPoolConfig.setMaxTotal(20);
        jedisPoolConfig.setMaxIdle(10);
        jedisPoolConfig.setMinIdle(5);
        String masterName = "mymaster";
        Set<String> sentinels=new HashSet<>();
        sentinels.add(new HostAndPort("192.168.89.4", 26379).toString());
        sentinels.add(new HostAndPort("192.168.89.4", 26380).toString());
        sentinels.add(new HostAndPort("192.168.89.4", 26381).toString());
        //JedisSentinelPool 本质上与jedispool一样，都是与redis主节点建立的连接池
        JedisSentinelPool jedisSentinelPool=new JedisSentinelPool(masterName, sentinels,
                jedisPoolConfig, 2000 ,null);
        Jedis jedis = null;
        try {
            jedis = jedisSentinelPool.getResource();
            log.info(jedis.set("sentinel","yyx"));
            log.info(jedis.get("sentinel"));
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            //这里不是关闭连接，在jedispool模式下，jedis会归还给资源池
            if (jedis != null) {
                jedis.close();
            }
        }
    }
}
