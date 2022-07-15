package com.tang.main.thread;

import java.util.*;
import java.util.concurrent.*;

public class CountDownLatchDemo {

    /**
     * 定义线程池
     */
    private static final ExecutorService THREAD_POOL = new ThreadPoolExecutor(5,10,1000,
            TimeUnit.SECONDS,new ArrayBlockingQueue<>(1000),new ThreadPoolExecutor.AbortPolicy());


    public static void main(String[] args) throws InterruptedException {
        Map<String,Integer> prices = new CountDownLatchDemo().getPrices();
        System.out.println(prices);
    }

    private Map<String,Integer> getPrices() throws InterruptedException {
//        Set<Integer> prices = Collections.synchronizedSet(new HashSet<Integer>());
        Map<String,Integer> prices = new ConcurrentHashMap<>();
        CountDownLatch countDownLatch = new CountDownLatch(3);
        String[] ids = new String[]{"1345","5463","9820"};
        Arrays.stream(ids).forEach(id->{
            THREAD_POOL.submit(new Task(id,prices,countDownLatch));
        });
//        Thread.sleep(1030);
        //countDownLatch的调用次数大于等于3，即使超时时间还没到都会立即返回，往下执行
        countDownLatch.await(3,TimeUnit.SECONDS);
        THREAD_POOL.shutdown();
        return prices;
    }

    class Task implements Runnable{

        private String id;
//        private Set<Integer> prices;
        private Map<String,Integer> prices;
        private CountDownLatch countDownLatch;
        public Task(String id, Map<String,Integer>prices){
            this.id = id;
            this.prices = prices;
        }

        public Task(String id, Map<String,Integer>prices,CountDownLatch countDownLatch){
            this.id = id;
            this.prices = prices;
            this.countDownLatch = countDownLatch;
        }

        @Override
        public void run() {
            int price=0;
            try {
                Thread.sleep((long) (Math.random() * 4000));
                price= (int) (Math.random() * 4000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            countDownLatch.countDown();
            prices.put(id,price);
        }
    }
}


