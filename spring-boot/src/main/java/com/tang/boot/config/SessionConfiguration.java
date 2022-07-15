package com.tang.boot.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;

/**
 * spring session 配置
 */
@Configuration
@EnableRedisHttpSession //自动化配置 Spring Session 使用 Redis 作为数据源
public class SessionConfiguration {

    @Bean(name = "springSessionDefaultRedisSerializer")
    public RedisSerializer springSessionDefaultRedisSerializer(){
        return RedisSerializer.json();
    }
}
