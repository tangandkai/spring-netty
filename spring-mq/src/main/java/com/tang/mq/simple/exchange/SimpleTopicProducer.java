package com.tang.mq.simple.exchange;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.ConfirmListener;
import com.rabbitmq.client.Connection;
import com.tang.mq.simple.ConnectFactory;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class SimpleTopicProducer {

    private static final String EXCHANGE_NAME = "topic_log1";

    private static final String TYPE = "topic";

    private static final String ROUTE_KEY = "direct_router_key_1";

    private static final String QUEUE_NAME = "topic_log_queue_1";

    public static void main(String[] args) throws InterruptedException {

        Thread first = new Thread(() -> {
            publish("这是一个topic交换机测试", ROUTE_KEY, QUEUE_NAME);
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
            channel.queueDeclare(queueName,true, false, false, null);
            channel.queueBind(queueName,EXCHANGE_NAME,routerKey);
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
