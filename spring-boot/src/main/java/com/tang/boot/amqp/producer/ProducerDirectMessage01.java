package com.tang.boot.amqp.producer;

import com.tang.boot.amqp.Message;
import com.tang.boot.config.RabbitConfig;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFuture;

import java.util.UUID;

@Component
public class ProducerDirectMessage01 {

    private RabbitTemplate rabbitTemplate;

    @Autowired
    public void setRabbitTemplate(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void syncSend(Integer id) {
        // 创建  消息
        Message message = new Message();
        message.setId(id);
        message.setMsg("==========ProducerMessage01=========syncSend========="+ UUID.randomUUID().toString());
        // 同步发送消息
        rabbitTemplate.convertAndSend(RabbitConfig.EXCHANGE, RabbitConfig.DIRECT_ROUTING_KEY, message);
    }

    public void syncSendDefault(Integer id) {
        // 创建 Demo01Message 消息
        Message message = new Message();
        message.setId(id);
        message.setMsg("==========ProducerMessage01=========syncSendDefault=========");
        // 同步发送消息
        rabbitTemplate.convertAndSend(RabbitConfig.DIRECT_QUEUE, message);
    }

    @Async
    public ListenableFuture<Void> asyncSend(Integer id) {
        try {
            // 发送消息
            this.syncSend(id);
            // 返回成功的 Future
            return AsyncResult.forValue(null);
        } catch (Throwable ex) {
            // 返回异常的 Future
            return AsyncResult.forExecutionException(ex);
        }
    }
}
