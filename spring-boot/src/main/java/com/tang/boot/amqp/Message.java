package com.tang.boot.amqp;

import lombok.Data;

import java.io.Serializable;

@Data
public class Message implements Serializable {

    /**
     * 编号
     */
    private Integer id;


    /**
     * 消息
     */
    private String msg;
}
