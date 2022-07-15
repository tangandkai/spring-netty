package com.tang.boot.config;

import com.jd.jim.cli.Cluster;
import com.jd.jim.cli.ReloadableJimClientFactory;
import com.jd.jim.cli.config.ConfigLongPollingClientFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;

/**
 * JimDb配置
 * @author tangwenkai
 * @date 2021-09-23 11:49
 */
//@Configuration
public class JimDbConfig {

    private final String jimUrl = "jim://2914179041246144323/110000253";
    private final String serviceEndpoint = "http://cfs.jim.jd.local/";
    protected ReloadableJimClientFactory factory = null;

    @Bean(name = "factory")
    public ReloadableJimClientFactory createFactory(){
        ConfigLongPollingClientFactory configClientFactory = new ConfigLongPollingClientFactory(serviceEndpoint);
        factory = new ReloadableJimClientFactory();
        factory.setJimUrl(jimUrl);
        factory.setIoThreadPoolSize(5);
        factory.setComputationThreadPoolSize(5);
        factory.setRequestQueueSize(100000);
        factory.setConfigClient(configClientFactory.create());
        return factory;
    }

    @Bean(name = "jimClient")
    @DependsOn("factory")
    public Cluster createClient(){
        return factory.getClient();
    }
}
