package com.tang.blog;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class SpringBlogsApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringBlogsApplication.class, args);
	}

}
