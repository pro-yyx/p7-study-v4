package com.yyx.redis.redisson;

import org.redisson.Redisson;
import org.redisson.config.Config;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

//@SpringBootApplication
public class BootstrapStarter{
    public static void main(String[] args) {
        SpringApplication.run(BootstrapStarter.class, args);
    }

    @Bean
    public Redisson redisson() {
        Config config=new Config();
        config.useClusterServers().addNodeAddress("redis://192.168.89.4:8001");
        config.useClusterServers().addNodeAddress("redis://192.168.89.4:8004");
        config.useClusterServers().addNodeAddress("redis://192.168.89.5:8002");
        config.useClusterServers().addNodeAddress("redis://192.168.89.5:8003");
        config.useClusterServers().addNodeAddress("redis://192.168.89.5:8005");
        config.useClusterServers().addNodeAddress("redis://192.168.89.5:8006");
        return (Redisson) Redisson.create(config);
    }
}
