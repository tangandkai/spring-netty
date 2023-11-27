package com.tang.mq.simple.channel;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.tang.mq.simple.ConnectFactory;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class SimpleDeadDirectConsumer {

    private static final String EXCHANGE_NAME = "direct_log1";

    private static final String EXCHANGE_NAME_DEAD = "direct_dead_log1";
    private static final String TYPE = "direct";

    private static final String ROUTE_KEY = "direct_router_key_2";

    private static final String QUEUE_NAME = "direct_queue_2";

    private static final String QUEUE_DEAD_NAME = "direct_dead_queue_1";
    private static final String ROUTE_KEY_DEAD = "direct_dead_router_key_2";

    public static void main(String[] args) throws InterruptedException {
        Thread first = new Thread(() -> {
            consumeNormal(QUEUE_NAME);
        }, "first");

        Thread second = new Thread(() -> {
            consume(QUEUE_DEAD_NAME);
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
                channel.basicConsume(queueName, true, (s, delivery) -> System.out.println("当前死信消费者"+Thread.currentThread().getName()+"消费的消息: "+new String(delivery.getBody())), s -> System.out.println("消费者取消消费的消息： " + s));
            }
        }catch (IOException e){
            e.printStackTrace();
        }finally {
            try {
                channel.close();
            }catch (Exception e){
                e.printStackTrace();
            }
            ConnectFactory.close(connection);
        }
    }

    public static void consumeNormal(String queueName)  {
        Connection connection = ConnectFactory.getConnection();
        Channel channel = ConnectFactory.getChannel(connection);
        try {
            assert channel != null;
//            Map<String,Object> arg = new HashMap<>(8);
            // 正常队列设置死信队列
//            arg.put("x-dead-letter-exchange",EXCHANGE_NAME_DEAD);
            // 设置死信routerKey
//            arg.put("x-dead-letter-routing-key",ROUTE_KEY_DEAD);
            // 过期时间 10s
//            arg.put("x-message-ttl",10000);
//            channel.queueDeclare(QUEUE_NAME,true,false,false,arg);
            int num=0;
            while (true){
                // 消费者队列  是否自动应答  消费者消费成功的回调  消费者取消消费的回调
                channel.basicConsume(queueName, false, (s, delivery) -> {
                    byte[] body = delivery.getBody();
                    long deliveryTag = delivery.getEnvelope().getDeliveryTag();
                    String msg = new String(body);
                    if (msg.contains("2")){
                        channel.basicReject(deliveryTag,false);
                    }else {
                        System.out.println("当前正常消费者"+Thread.currentThread().getName()+"消费的消息: "+new String(delivery.getBody()));
                        channel.basicAck(deliveryTag,false);
                    }
                }, s -> System.out.println("消费者取消消费的消息： " + s));
            }
        }catch (IOException  e){
            e.printStackTrace();
        } finally {
            try {
                channel.close();
            }catch (Exception e){
                e.printStackTrace();
            }
            ConnectFactory.close(connection);
        }
    }
}
