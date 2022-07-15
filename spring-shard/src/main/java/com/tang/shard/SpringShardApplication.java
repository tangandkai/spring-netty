package com.tang.shard;

import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceAutoConfigure;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication(exclude = {DruidDataSourceAutoConfigure.class})
@EnableDiscoveryClient
@MapperScan("com.tang.shard.mapper")
public class SpringShardApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringShardApplication.class, args);
	}
}
