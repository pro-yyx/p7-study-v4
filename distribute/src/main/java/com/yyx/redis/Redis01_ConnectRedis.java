package com.yyx.redis;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.Pipeline;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Slf4j
public class Redis01_ConnectRedis {
    public static void main(String[] args) {
        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
        jedisPoolConfig.setMaxTotal(20);
        jedisPoolConfig.setMaxIdle(10);
        jedisPoolConfig.setMinIdle(5);

        //timeout,写和读的超时,从jedis2.8开始区分connectiorTimeout和sotimeout的构造函数
        JedisPool jedisPool=new JedisPool(jedisPoolConfig, "192.168.89.4",6379 ,3000 ,null );
        Jedis jedis = null;
        try {
            //从redis连接池里拿出一个连接执行命令
            jedis = jedisPool.getResource();
            log.info(jedis.set("jedis-test","yyx"));
            log.info(jedis.get("jedis-test"));
            //管道示例
            log.info("开始测试pipeline");
            Pipeline pipeline = jedis.pipelined();
            for (int i = 0; i < 9; i++) {
                pipeline.incr("jedis-test-pipeline");
                pipeline.set("jedis-test" + i, "yyx" + i);
            }
            List<Object> objects = pipeline.syncAndReturnAll();
            log.info("pipeline result:"+ JSONObject.toJSONString(objects));

            log.info("开始测试lua脚本");
            //设置产品001的库存为15
            jedis.set("product_stock_001", "15");
            String script = "local count = redis.call('get',KEYS[1]) " +
                    "local a=tonumber(count) " +
                    "local b =tonumber(ARGV[1]) " +
                    "if a>=b then " +
                    "redis.call('set',KEYS[1],a-b) " +
                    "return 1 end " +
                    "return 0";
            Object ob = jedis.eval(script, Arrays.asList("product_stock_001"), Arrays.asList("10"));
            log.info("eval result:{}",JSONObject.toJSONString(ob));


        } catch (Exception e) {
            e.printStackTrace();
        }finally {

        }
    }
}
