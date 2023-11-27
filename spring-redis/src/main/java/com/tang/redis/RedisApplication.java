package com.tang.redis;

import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceAutoConfigure;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Hello world!
 *
 */
@SpringBootApplication(exclude = {DruidDataSourceAutoConfigure.class})
public class RedisApplication
{
    public static void main( String[] args ) {
        SpringApplication.run(RedisApplication.class, args);
    }
}
