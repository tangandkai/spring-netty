package com.tang.mq.simple;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 * 生产者
 */
public class SimpleMultiProducer {


    private static final String QUEUE_NAME = "hello";

    public static void main(String[] args) throws InterruptedException {
        Connection connection = ConnectFactory.getConnection();
        Channel channel = ConnectFactory.getChannel(connection);
        Thread first = new Thread(() -> {
            int n = 10;
            try {
                // 队列名称  是否持久化  是否消息共享  是否自动删除  其他参数
                channel.queueDeclare(QUEUE_NAME, true, false, false, null);
                while (n > 0) {
                    n--;
                    String message = "hello word simpleMultiConsumer first: " + n;
                    channel.basicPublish("", QUEUE_NAME, null, message.getBytes(StandardCharsets.UTF_8));
                    Thread.sleep(12);
                }
            } catch (IOException | InterruptedException e) {
                e.printStackTrace();
            }
        }, "SimpleMultiConsumer first");
        first.start();
        first.join();
        Thread second = new Thread(() -> {
            int n = 11;
            try {
                // 队列名称  是否持久化  是否消息共享  是否自动删除  其他参数
                channel.queueDeclare(QUEUE_NAME, true, false, false, null);
                while (n > 0) {
                    n--;
                    String message = "hello word simpleMultiConsumer second: " + n;
                    channel.basicPublish("", QUEUE_NAME, null, message.getBytes(StandardCharsets.UTF_8));
                    Thread.sleep(10);
                }
            } catch (IOException | InterruptedException e) {
                e.printStackTrace();
            }
        }, "SimpleMultiConsumer second");
        second.start();
        second.join();
        ConnectFactory.close(connection);
    }
}
