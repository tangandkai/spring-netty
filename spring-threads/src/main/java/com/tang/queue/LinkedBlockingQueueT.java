package com.tang.queue;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class LinkedBlockingQueueT {

    public static void main(String[] args) {
        BlockingQueue<Object> blockingQueue = new LinkedBlockingQueue<>();
        blockingQueue.add("nice");

    }
}
