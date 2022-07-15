package com.tang.oauthserver.springauthorizationserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class SpringAuthorizationServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringAuthorizationServerApplication.class, args);
    }

}
