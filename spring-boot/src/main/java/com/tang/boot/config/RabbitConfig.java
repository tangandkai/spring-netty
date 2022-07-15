package com.tang.boot.config;

import org.springframework.amqp.core.*;
import org.springframework.boot.autoconfigure.amqp.RabbitProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(RabbitProperties.class)
public class RabbitConfig {

    public static final String DIRECT_QUEUE = "direct_queue";
    public static final String EXCHANGE = "studyExchange_1";
    public static final String DIRECT_ROUTING_KEY = "direct_router_key";

    public static final String TOPIC_QUEUE = "direct_queue";
    public static final String TOPIC_ROUTING_KEY = "topic.router.key";

    /**
     * Direct Exchange 示例的配置类
     */
    public static class DirectExchangeConfiguration{

        /**
         * 创建队列
         * @return
         */
        @Bean
        public Queue directQueue(){
            return new Queue(DIRECT_QUEUE,true,false,false);
        }

        /**
         * 创建Direct Exchange
         * @return
         */
        @Bean
        public DirectExchange directExchange(){
            return new DirectExchange(EXCHANGE,true,false);
        }

        /**
         * 配置队列和交换器的的绑定key
         * 客户端向rabbitmq中exchange发送消息时，会根据路由key将消息发送到对应的队列中
         * @return
         */
        @Bean
        public Binding directBinding(){
            return BindingBuilder.bind(directQueue()).to(directExchange()).with(DIRECT_ROUTING_KEY);
        }
    }

    public static class TopicExchangeConfiguration{
        /**
         * 创建队列
         * @return
         */
        @Bean
        public Queue topicQueue(){
            return new Queue(TOPIC_QUEUE,true,false,false);
        }

        /**
         * 创建Direct Exchange
         * @return
         */
        @Bean
        public TopicExchange topicExchange(){
            return new TopicExchange(EXCHANGE,true,false);
        }

        /**
         * 配置队列和交换器的的绑定key
         * 客户端向rabbitmq中exchange发送消息时，会根据路由key将消息发送到对应的队列中
         * @return
         */
        @Bean
        public Binding topicBinding(){
            return BindingBuilder.bind(topicQueue()).to(topicExchange()).with(TOPIC_ROUTING_KEY);
        }
    }

    public static class FanoutExchangeConfiguration{
        /**
         * 创建队列
         * @return
         */
        @Bean
        public Queue fanoutQueue(){
            return new Queue(TOPIC_QUEUE,true,false,false);
        }

        /**
         * 创建Direct Exchange
         * @return
         */
        @Bean
        public FanoutExchange fanoutExchange(){
            return new FanoutExchange(EXCHANGE,true,false);
        }

        /**
         * 配置队列和交换器的的绑定key
         * 客户端向rabbitmq中exchange发送消息时，会根据路由key将消息发送到对应的队列中
         * @return
         */
        @Bean
        public Binding fanoutBinding(){
            return BindingBuilder.bind(fanoutQueue()).to(fanoutExchange());
        }
    }
}
