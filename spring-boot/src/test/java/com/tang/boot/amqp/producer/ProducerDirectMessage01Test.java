package com.tang.boot.amqp.producer;

import com.tang.boot.ApplicationTests;
import org.junit.jupiter.api.Test;
import org.springframework.util.concurrent.ListenableFuture;

import javax.annotation.Resource;

import java.util.UUID;

class ProducerDirectMessage01Test extends ApplicationTests {

    @Resource
    private ProducerDirectMessage01 producerDirectMessage01;

    @Test
    void syncSend() throws InterruptedException {
        while (true){
            producerDirectMessage01.syncSend(UUID.randomUUID().variant());
            Thread.sleep(1000);
        }
//        new CountDownLatch(1).await();
    }

    @Test
    void syncSendDefault() {
        producerDirectMessage01.syncSendDefault(UUID.randomUUID().variant());
    }

    @Test
    void asyncSend() {
        ListenableFuture<Void> voidListenableFuture = producerDirectMessage01.asyncSend(UUID.randomUUID().variant());
    }
}