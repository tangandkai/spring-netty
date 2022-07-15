package com.tang.blog.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;

@Configuration
@EnableRedisHttpSession //自动化配置 Spring Session 使用 Redis 作为数据源
public class SessionConfiguration {

    /**
     * 它会引入名字为 "springSessionDefaultRedisSerializer" 的 Bean 。
     *采用 JSON 序列化方式。因为默认情况下，采用 Java 自带的序列化方式 ，可读性很差，所以进行替换。
     * 目前整合auth模块会报错，只能使用java默认的序列化方式
     * HttpSession 的 attributes 属性，是 Map<String, Object> 类型。
     * 在序列化 Session 到 Redis 中时，不存在问题。
     * 在反序列化 Redis 的 key-value 键值对成 Session 时，
     * 如果 attributes 的 value 存在 POJO 对象的时候，因为不知道该 value 是什么 POJO 对象，导致无法反序列化错误。
     * @return RedisSerializer Bean
     */
//    @Bean(name = "springSessionDefaultRedisSerializer")
//    public RedisSerializer springSessionDefaultRedisSerializer() {
//        return RedisSerializer.json();
//    }
}
