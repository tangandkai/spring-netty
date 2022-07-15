package com.tang.boot;

import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceAutoConfigure;
import com.tang.boot.config.MyNameGenerator;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ImportResource;
import org.springframework.scheduling.annotation.EnableAsync;

/**
 * springboot启动
 * @author tangwenkai
 * @date 2021-06-07 21:20
 */
@SpringBootApplication(exclude = {DruidDataSourceAutoConfigure.class})
@ComponentScan(basePackages = "com.tang.boot",nameGenerator = MyNameGenerator.class)
@MapperScan("com.tang.boot.mapper")
@EnableDiscoveryClient
@EnableCaching
@ImportResource(locations = {"classpath:spring/spring-profile.xml"})
@EnableAsync //开启异步
public class SpringbootApplication {
}