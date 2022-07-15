package com.tang.sureness.config;

import com.usthe.sureness.DefaultSurenessConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * sureness配置
 */
@Configuration
public class SurenessConfiguration {

    /**
     * 使用sureness默认的方式读取配置
     * 该方式读取的是文件中的配置
     * 来自sureness.yml
     * @return
     */
    @Bean
    public DefaultSurenessConfig surenessConfig(){
        return new DefaultSurenessConfig();
    }
}
