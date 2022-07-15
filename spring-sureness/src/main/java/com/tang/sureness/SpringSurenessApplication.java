package com.tang.sureness;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
@ServletComponentScan
public class SpringSurenessApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringSurenessApplication.class, args);
	}
}
