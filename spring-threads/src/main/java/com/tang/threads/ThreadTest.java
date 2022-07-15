package com.tang.threads;

import java.util.concurrent.CountDownLatch;

public class ThreadTest {

    private static int n = 0;
    public static void main(String[] args) {
        CountDownLatch countDownLatch = new CountDownLatch(1);
        for (int i=1;i<=10;i++){
            int finalI = i;
            new Thread(() -> {
                try {
                    countDownLatch.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                synchronized (ThreadTest.class){
                    n = finalI;
                    try {
                        Thread.sleep(5000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println(Thread.currentThread().getName()+"   "+n);
                }

            },"thread--"+i).start();
        }

        System.out.println("main start.................");
        countDownLatch.countDown();
    }
}
