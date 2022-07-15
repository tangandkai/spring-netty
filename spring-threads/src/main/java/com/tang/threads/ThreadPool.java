package com.tang.threads;

import com.alibaba.fastjson.support.geo.LineString;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.SneakyThrows;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.*;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 线程池样例测试
 * @author tangwenkai
 * @date 2021-05-31 15:23
 */
public class ThreadPool {

    static Lock lock = new ReentrantLock();
    public static void main(String[] args) throws ExecutionException, InterruptedException {

        ThreadPoolExecutor poolExecutor = new ThreadPoolExecutor(5,
                10,
                5,
                TimeUnit.SECONDS,
                new LinkedBlockingQueue<Runnable>(100),
                new ThreadPoolExecutor.DiscardPolicy());

        t1(poolExecutor);
        t1(poolExecutor);
    }

    public static void t1(ThreadPoolExecutor poolExecutor) throws ExecutionException, InterruptedException {
        List<Future> list = new LinkedList<>();
        for (int i=0;i<10;i++){
            Future<String> submit = poolExecutor.submit(new Callable<String>() {
                @Override
                public String call() throws Exception {
                    return Thread.currentThread().getName()+" nice to meet you....";
                }
            });
            list.add(submit);
        }
        poolExecutor.shutdown();
        while (true){
            if (poolExecutor.isTerminated()){
                for (Future future:list){
                    Object o = future.get();
                    System.out.println(o);
                }
                break;
            }
        }
    }
}