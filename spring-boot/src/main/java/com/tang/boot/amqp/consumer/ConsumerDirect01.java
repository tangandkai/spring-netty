package com.tang.boot.amqp.consumer;

import com.tang.boot.amqp.Message;
import com.tang.boot.config.RabbitConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @RabbitListener 注解，声明了消费的队列是 "RabbitConfig.DIRECT_QUEUE"
 * @RabbitHandler 注解，申明了处理消息的方法。同时，方法入参为消息的类型
 */
@Component
@RabbitListener(queues = RabbitConfig.DIRECT_QUEUE,ackMode = "NONE")
@Slf4j
public class ConsumerDirect01 {

    @RabbitHandler
    public void onMessage(Message message) {
        log.info("[onMessage][线程编号:{} 消息内容：{}]", Thread.currentThread().getId(), message);
    }

}
