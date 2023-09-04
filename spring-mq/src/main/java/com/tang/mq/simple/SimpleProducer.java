package com.tang.mq.simple;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 * 生产者
 */
public class SimpleProducer {


    private static final String QUEUE_NAME = "hello";

    public static void main(String[] args) throws IOException {
        Connection connection = ConnectFactory.getConnection();
        Channel channel = ConnectFactory.getChannel(connection);
        // 队列名称  是否持久化  是否消息共享  是否自动删除  其他参数
        AMQP.Queue.DeclareOk queueDeclare = channel.queueDeclare(QUEUE_NAME, true, false, false, null);

        String message = "hello word";
        channel.basicPublish("",QUEUE_NAME,null,message.getBytes(StandardCharsets.UTF_8));
        ConnectFactory.close(connection);
    }
}
