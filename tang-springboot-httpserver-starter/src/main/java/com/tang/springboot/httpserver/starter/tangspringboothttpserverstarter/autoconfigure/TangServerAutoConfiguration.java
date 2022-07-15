package com.tang.springboot.httpserver.starter.tangspringboothttpserverstarter.autoconfigure;

import com.sun.net.httpserver.HttpServer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.net.InetSocketAddress;

@Configuration
@EnableConfigurationProperties(TangServerProperties.class)
@Slf4j
public class TangServerAutoConfiguration {

    @Bean
    @ConditionalOnClass(HttpServer.class)
    public HttpServer httpServer(TangServerProperties serverProperties) throws IOException {
        // 创建 HttpServer 对象，并启动
        HttpServer server = HttpServer.create(new InetSocketAddress(serverProperties.getPort()), 0);
        server.start();
        log.info("[httpServer][启动服务器成功，端口为:{}]", serverProperties.getPort());
        // 返回
        return server;
    }
}
