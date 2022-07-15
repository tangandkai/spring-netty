package com.tang.main.thread;

import lombok.SneakyThrows;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;
import java.util.stream.IntStream;

public class ThreadSemaphore {

    /**
     * 定义线程池
     */
    private static final ExecutorService THREAD_POOL = new ThreadPoolExecutor(5,10,1000,
            TimeUnit.SECONDS,new ArrayBlockingQueue<>(1000),new ThreadPoolExecutor.AbortPolicy());


    public static void main(String[] args) {
        List<String> list = new ThreadSemaphore().run();
        System.out.println(list);

    }

    private List<String> run(){
        List<String> list = new ArrayList<>();
        CountDownLatch countDownLatch = new CountDownLatch(10);
        Semaphore semaphore = new Semaphore(3, true);
        IntStream.range(0,10).forEach(i->{
            THREAD_POOL.submit(new Task<String>(semaphore,list,countDownLatch));
        });
        try {
//            countDownLatch.await(12,TimeUnit.SECONDS);
            countDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        THREAD_POOL.shutdown();
        return list;
    }

    private class Task<T> implements Runnable{

        private List<T> list;
        private Semaphore semaphore;
        private CountDownLatch countDownLatch;

        public Task(Semaphore semaphore,List<T> list,CountDownLatch countDownLatch){
            this.list = list;
            this.semaphore = semaphore;
            this.countDownLatch = countDownLatch;
        }
        @Override
        public void run() {
            try {
                // 获取信号量
                semaphore.acquire();
                Thread.sleep(2000);
                countDownLatch.countDown();
                System.out.println(Thread.currentThread().getName()+"执行完毕");
                list.add((T) (Thread.currentThread().getName()+"执行完毕"));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }finally {
                // 释放信号量
                semaphore.release();
            }
        }
    }
}
