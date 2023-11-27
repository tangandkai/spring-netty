package com.tang.mq.simple.exchange;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.ConfirmListener;
import com.rabbitmq.client.Connection;
import com.tang.mq.simple.ConnectFactory;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class SimpleFanOutProducer {

    private static final String EXCHANGE_NAME = "fantout_log1";

    private static final String TYPE = "fanout";

    public static void main(String[] args) {

        publish("这是一个fantout交换机测试");
    }

    public static void publish(String msg)  {
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
//            channel.queueDeclare()
            while (n>0){
                n--;
                String message = msg + n;
                channel.basicPublish(EXCHANGE_NAME,"",null,message.getBytes(StandardCharsets.UTF_8));
            }

        }catch (IOException e){
            e.printStackTrace();
        }finally {
            ConnectFactory.close(connection);
        }
    }
}
