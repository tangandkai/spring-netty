package com.tang.mq.simple;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.ConfirmListener;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.MessageProperties;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.ConcurrentNavigableMap;
import java.util.concurrent.ConcurrentSkipListMap;

/**
 * 生产者
 */
public class SimpleMultiProducer {


    private static final String QUEUE_NAME = "multi_hello";
    static ConcurrentSkipListMap<Long ,String> map = new ConcurrentSkipListMap<>();

    public static void main(String[] args) throws InterruptedException, IOException {
        Thread first = new Thread(() -> {
            publish( "SimpleMultiConsumer first 发送消息");
        }, "SimpleMultiConsumer first");
        Thread second = new Thread(() -> {
            publish( "SimpleMultiConsumer second 发送消息");
        }, "SimpleMultiConsumer second");
        first.start();
        second.start();
        first.join();
        second.join();
        Thread.sleep(100);
        System.out.println("消息发送完毕");
    }

    private static void publish(String msg){
        Connection connection = ConnectFactory.getConnection();
        int n = 2;
        Channel channel = ConnectFactory.getChannel(connection);
        try {
            assert channel != null;
            // 队列名称  是否持久化  是否消息共享  是否自动删除  其他参数
            channel.queueDeclare(QUEUE_NAME, true, false, false, null);
            // 开启发布确认
            channel.confirmSelect();
            // 设置不公平分发
            channel.basicQos(1);
            channel.addConfirmListener(new ConfirmListener() {
                @Override
                public void handleAck(long deliveryTag, boolean multiple) {
                    System.out.println("成功完成消息投送: " +map.get(deliveryTag) +" 对应的tag是: "+deliveryTag);
                    // 成功回调
                    if (multiple){
                        ConcurrentNavigableMap<Long, String> headMap = map.headMap(deliveryTag);
                        headMap.clear();
                    }else {
                        map.remove(deliveryTag);
                    }
                }
                @Override
                public void handleNack(long deliveryTag, boolean multiple) {
                    // 失败回调
                    System.out.println("未确认的消息 : "+deliveryTag);
                }
            });
            while (n > 0) {
                n--;
                String message = msg +": "+ n;
                channel.basicPublish("", QUEUE_NAME, MessageProperties.PERSISTENT_TEXT_PLAIN, message.getBytes(StandardCharsets.UTF_8));
                map.put(channel.getNextPublishSeqNo(),message);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            ConnectFactory.close(connection);
        }
    }
}
