package com.tang.mq.simple;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.Objects;
import java.util.concurrent.TimeoutException;

public class ConnectFactory {

    private static ConnectionFactory connectionFactory = new ConnectionFactory();
    static {
        connectionFactory.setHost("localhost");
        connectionFactory.setUsername("guest");
        connectionFactory.setPassword("guest");
    }

    public static Connection getConnection(){
        try {
            return connectionFactory.newConnection();
        } catch (IOException | TimeoutException e) {
            e.printStackTrace();
        }
        return  null;
    }

    /**
     * 获取信道
     * @param connection
     * @return
     */
    public static Channel getChannel(Connection connection) {
        try {
            return connection.createChannel();
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    public static void close(Connection connection){
        try {
            if (Objects.nonNull(connection)){
                connection.close();
            }
        }catch (IOException e){
            e.printStackTrace();
        }
    }
}
