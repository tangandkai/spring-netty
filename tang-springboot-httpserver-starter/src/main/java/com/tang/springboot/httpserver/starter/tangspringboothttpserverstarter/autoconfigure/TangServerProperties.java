package com.tang.springboot.httpserver.starter.tangspringboothttpserverstarter.autoconfigure;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "tang.server")
public class TangServerProperties {

    /**
     * 默认端口
     */
    private static final Integer DEFAULT_PORT = 8080;

    /**
     * 端口
     */
    private Integer port = DEFAULT_PORT;

    public Integer getPort() {
        return port;
    }

    public TangServerProperties setPort(Integer port) {
        this.port = port;
        return this;
    }

    public static Integer getDefaultPort() {
        return DEFAULT_PORT;
    }
}
