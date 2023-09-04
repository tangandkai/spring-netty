package com.tang.mq.simple;

import com.rabbitmq.client.*;

import java.io.IOException;

/**
 * 消费者
 */
public class SimpleConsumer {
    private static final String QUEUE_NAME = "hello";

    public static void main(String[] args) throws IOException {

        Connection connection = ConnectFactory.getConnection();
        Channel channel = ConnectFactory.getChannel(connection);

        // 消费者队列  是否自动应答  消费者消费成功的回调  消费者取消消费的回调
        String consume = channel.basicConsume(QUEUE_NAME, true, (s, delivery) -> System.out.println(new String(delivery.getBody())), s -> System.out.println("消费者取消消费的消息： " + s));
        System.out.println("消费消息内容: "+consume);
        ConnectFactory.close(connection);
    }


}
