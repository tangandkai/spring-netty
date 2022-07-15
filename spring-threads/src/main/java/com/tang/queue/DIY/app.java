package com.tang.queue.DIY;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class app {

    private final static Queue<String> queue = new DIYQueue<>();
    public static void main(String[] args) throws InterruptedException {
        ThreadPoolExecutor executor = new ThreadPoolExecutor(5,
                10,
                5,
                TimeUnit.SECONDS,
                new LinkedBlockingQueue<Runnable>(100),
                new ThreadPoolExecutor.DiscardPolicy());

        for (int i = 0; i < 10; i++) {
            // 是偶数的话，就提交一个生产者，奇数的话提交一个消费者
            if(i % 2 == 0){
                executor.submit(new Product(i+""));
                continue;
            }
            executor.submit(new Consumer());
            Thread.sleep(1000);
        }
        executor.shutdown();
    }
    static class Product implements Runnable{

        private String message;
        public Product(String message){
            this.message = message;
        }
        @Override
        public void run() {
            try {
                boolean success = queue.put(message);
                if (success) {
                    System.out.println("put "+message+" success");
                    return;
                }
                System.out.println("put "+message+" fail");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    static class Consumer implements Runnable{

        @Override
        public void run() {
            try {
                String message = (String) queue.take();
                System.out.println("consumer message :"+message);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
