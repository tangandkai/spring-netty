package com.tang.threads;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * SynchronousQueue 测试
 * @author tangwenkai
 * @date 2021-06-01 15:54
 */
public class MySynchronousQueue {

    public static void main(String[] args) {
        ThreadPoolExecutor poolExecutor = new ThreadPoolExecutor(5,
                10,
                5,
                TimeUnit.SECONDS,
                new LinkedBlockingQueue<Runnable>(100),
                new ThreadPoolExecutor.DiscardPolicy());
        SynchronousQueue<Integer> queue = new SynchronousQueue<>(true);

        poolExecutor.execute(()->{
            try {
                queue.put(2);
                queue.put(3);
                queue.put(4);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        poolExecutor.execute(()->{
            try {
                queue.put(5);
                queue.put(6);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        poolExecutor.execute(()->{
            for (int i=0;i<5;i++){
                try {
                    System.out.println("SynchronousQueue rs "+queue.take());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        poolExecutor.shutdown();
    }
}
