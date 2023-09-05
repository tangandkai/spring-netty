package com.tang.mq.simple;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

import java.io.IOException;

/**
 * 消费者
 */
public class SimpleMultiConsumer {
    private static final String QUEUE_NAME = "multi_hello";

    public static void main(String[] args) throws IOException, InterruptedException {

        Connection connection = ConnectFactory.getConnection();
        Channel channel = ConnectFactory.getChannel(connection);
        Thread first = new Thread(() -> {
            try {
                while (true){
                    // 消费者队列  是否自动应答  消费者消费成功的回调  消费者取消消费的回调
                    channel.basicConsume(QUEUE_NAME, false, (s, delivery) -> {
                        System.out.println("消费者SimpleMultiConsumer first 消费的消息: "+new String(delivery.getBody())+"消息tag是: "+delivery.getEnvelope().getDeliveryTag());
                        channel.basicAck(delivery.getEnvelope().getDeliveryTag(),false);
                    }, s -> System.out.println("消费者SimpleMultiConsumer first取消消费的消息： " + s));
                    Thread.sleep(11);
                }
            } catch (IOException | InterruptedException e) {
                e.printStackTrace();
            }
        }, "SimpleMultiConsumer first");

        Thread second = new Thread(() -> {
            try {
                while (true){
                    // 消费者队列  是否自动应答  消费者消费成功的回调  消费者取消消费的回调
                    channel.basicConsume(QUEUE_NAME, false, (s, delivery) -> {
                        System.out.println("消费者SimpleMultiConsumer second消费的消息: "+new String(delivery.getBody())+"消息tag是: "+delivery.getEnvelope().getDeliveryTag());
                        channel.basicAck(delivery.getEnvelope().getDeliveryTag(),false);
                    }, s -> System.out.println("消费者SimpleMultiConsumer second取消消费的消息： " + s));
                    Thread.sleep(9);
                }
            } catch (IOException | InterruptedException e) {
                e.printStackTrace();
            }
        }, "SimpleMultiConsumer second");
        first.start();
        second.start();
        first.join();
        second.join();
        ConnectFactory.close(connection);
    }
}
