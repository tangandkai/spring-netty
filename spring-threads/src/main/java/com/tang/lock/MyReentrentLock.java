package com.tang.lock;

import java.util.Random;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class MyReentrentLock {

    private Lock lock = new ReentrantLock();
    public static void main(String[] args) throws InterruptedException {
        MyReentrentLock re = new MyReentrentLock();
        ThreadPoolExecutor poolExecutor = new ThreadPoolExecutor(5,
                10,
                5,
                TimeUnit.SECONDS,
                new LinkedBlockingQueue<Runnable>(100),
                new ThreadPoolExecutor.DiscardPolicy());
        for (int i=0;i<10;i++){
            Future<?> future = poolExecutor.submit(() -> {
                re.test_3();
            });
//            future.cancel(true);
        }
        poolExecutor.shutdown();
    }

    public void test_1(){
        lock.lock();
        try {
            System.out.println(Thread.currentThread().getName()+" test_1 lock");
        }finally {
            lock.unlock();
        }
    }

    public void test_2(){
        while (true){
            if (lock.tryLock()){
                try {
                    System.out.println(Thread.currentThread().getName()+" test_2 lock");
                    return;
                }finally {
                    lock.unlock();
                }
            }else {
                System.out.println(Thread.currentThread().getName()+" test_2 not get lock");
                try {
                    Thread.sleep( new Random().nextInt(1000));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void test_3() {
        try {
            lock.lockInterruptibly();
            try {
                System.out.println(Thread.currentThread().getName()+" test_3 lock");
            }finally {
                lock.unlock();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
