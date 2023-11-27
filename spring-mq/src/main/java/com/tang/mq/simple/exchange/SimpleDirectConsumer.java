package com.tang.mq.simple.exchange;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.tang.mq.simple.ConnectFactory;

import java.io.IOException;

public class SimpleDirectConsumer {

    public static void main(String[] args) throws InterruptedException {
        Thread first = new Thread(() -> {
            consume("direct_queue_1");
        }, "first");

        Thread second = new Thread(() -> {
            consume("direct_queue_2");
        }, "second");

        first.start();
        second.start();
        first.join();
        second.join();
    }

    public static void consume(String queueName)  {
        Connection connection = ConnectFactory.getConnection();
        Channel channel = ConnectFactory.getChannel(connection);

        try {
            assert channel != null;
            while (true){
                // 消费者队列  是否自动应答  消费者消费成功的回调  消费者取消消费的回调
                channel.basicConsume(queueName, true, (s, delivery) -> System.out.println("当前消费者"+Thread.currentThread().getName()+"消费的消息: "+new String(delivery.getBody())), s -> System.out.println("消费者取消消费的消息： " + s));
            }
        }catch (IOException e){
            e.printStackTrace();
        }finally {
            ConnectFactory.close(connection);
        }
    }
}
