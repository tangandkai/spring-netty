package com.tang.lock;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * 读写锁测试
 * @author tangwenkai
 * @date 2021-06-10 18:00
 */
public class ReadAndWriteLock {

    private static final ReadWriteLock lock = new ReentrantReadWriteLock();
    private static final Lock readLock = lock.readLock();
    private static final Lock writeLock = lock.writeLock();

    private static final Map<Integer,Integer> writeMap = new HashMap<>();
    private static final Map<Integer,Integer> readMap = new HashMap<>();

    static {
        for (int i=0;i<10;i++){
            writeMap.put(i,i);
            readMap.put(i,i);
        }
    }

    public static void read(int i){
        readLock.lock();
        try {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread().getName()+"\t"+"start read "+readMap.get(i));
        }finally {
            readLock.unlock();
        }
    }

    public static void write(int i){
        writeLock.lock();
        try {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread().getName()+"\t"+"start write "+i);
            writeMap.put(i,i*3);
            System.out.println(Thread.currentThread().getName()+"\t"+"write result "+writeMap.get(i));
        }finally {
            writeLock.unlock();
        }
    }

    public static void main(String[] args) {
        ThreadPoolExecutor poolExecutor = new ThreadPoolExecutor(5,
                10,
                5,
                TimeUnit.SECONDS,
                new LinkedBlockingQueue<Runnable>(100),
                new ThreadPoolExecutor.DiscardPolicy());

        for (int i=0;i<5;i++){
            int finalI = i;
            poolExecutor.execute(()->{
                read(finalI);
            });
        }
        poolExecutor.shutdown();
    }
}
