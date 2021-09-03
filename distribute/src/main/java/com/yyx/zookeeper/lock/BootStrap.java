package com.yyx.zookeeper.lock;

import org.springframework.boot.SpringApplication;

//@SpringBootApplication
//@ComponentScan(basePackages = "{com.yyx.zookpper.lock}")
//@MapperScan(basePackages = {"com.yyx.zookeeper.lock.dao"})
public class BootStrap {

    public static void main(String[] args) {
        SpringApplication.run(BootStrap.class, args);
    }
}
