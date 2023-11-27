package com.tang.mq.simple.channel;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.ConfirmListener;
import com.rabbitmq.client.Connection;
import com.tang.mq.simple.ConnectFactory;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class SimpleDeadDirectProducer {

    private static final String EXCHANGE_NAME = "direct_log1";
    private static final String TYPE = "direct";
    private static final String ROUTE_KEY = "direct_router_key_2";
    private static final String QUEUE_NAME = "direct_queue_2";

    public static void main(String[] args) throws InterruptedException {

        Thread first = new Thread(() -> {
            publish("这是一个死信direct交换机测试", ROUTE_KEY, QUEUE_NAME);
        }, "first");
        first.start();
        first.join();

    }

    public static void publish(String msg,String routerKey,String queueName)  {
        Connection connection = ConnectFactory.getConnection();
        Channel channel = ConnectFactory.getChannel(connection);
        int n = 4;
        try {
            assert channel != null;
            channel.confirmSelect();
            channel.addConfirmListener(new ConfirmListener() {
                @Override
                public void handleAck(long deliveryTag, boolean multiple) {
                    System.out.println("成功完成消息投送: "+deliveryTag);
                }
                @Override
                public void handleNack(long deliveryTag, boolean multiple) {
                    // 失败回调
                    System.out.println("未确认的消息 : "+deliveryTag);
                }
            });
            // 声明交换机
//            channel.exchangeDeclare(EXCHANGE_NAME,TYPE);
            // 声明队列
//            channel.queueDeclare(queueName,true, false, false, null);
//            channel.queueBind(queueName,EXCHANGE_NAME,routerKey);
            // 过期时间 10s
            AMQP.BasicProperties basicProperties = new AMQP.BasicProperties().builder().expiration("1").build();
            while (n>0){
                n--;
                String message = msg + n;
                channel.basicPublish(EXCHANGE_NAME,routerKey,null,message.getBytes(StandardCharsets.UTF_8));
            }
        }catch (IOException e){
            e.printStackTrace();
        }finally {
            ConnectFactory.close(connection);
        }
    }
}
