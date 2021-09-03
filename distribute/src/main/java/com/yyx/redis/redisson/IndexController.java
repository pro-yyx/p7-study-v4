package com.yyx.redis.redisson;

import lombok.extern.slf4j.Slf4j;
import org.redisson.Redisson;
import org.redisson.api.RLock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Slf4j
@RestController
public class IndexController {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    @Autowired
    private Redisson redisson;

    @RequestMapping("/deduct_stock")
    public String deductStock() {
        String lockKey = "stock_springbootredis";
        String lockValue = UUID.randomUUID().toString();
        try {
            Boolean aBoolean = stringRedisTemplate.opsForValue().setIfAbsent(lockKey, lockValue, 10, TimeUnit.SECONDS);
            if (!aBoolean) {
                return "fail";
            }
            int stock = Integer.parseInt(stringRedisTemplate.opsForValue().get("stock"));
            if (stock > 0) {
                int realStock = stock - 1;
                stringRedisTemplate.opsForValue().set("stock", realStock + "");
                log.info("扣减成功,剩余库存:{}", realStock);
            } else {
                log.info("扣减失败，库存不足");
            }
            return "end";
        } catch (NumberFormatException e) {
            e.printStackTrace();
        } finally {
            if (lockValue.equals(stringRedisTemplate.opsForValue().get(lockKey))) {
                stringRedisTemplate.delete(lockKey);
            }
        }
        return "failend";
    }

    @RequestMapping("/redisson/deduct_stock")
    public String deductStock_reddison() {
        String lockKey = "stock_redisson";
        RLock stock_redisson = redisson.getLock(lockKey);
        try {
            int stock = Integer.parseInt(stringRedisTemplate.opsForValue().get("stock"));

            stock_redisson.lock();
            if (stock > 0) {
                int realStock = stock - 1;
                stringRedisTemplate.opsForValue().set("stock", realStock + "");
                log.info("扣减成功,剩余库存:{}", realStock);
            } else {
                log.info("扣减失败，库存不足");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            stock_redisson.unlock();
        }
        return "end";
    }
}
