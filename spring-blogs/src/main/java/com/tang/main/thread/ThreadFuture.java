package com.tang.main.thread;

import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.*;
import java.util.stream.IntStream;

public class ThreadFuture {
    private static final ExecutorService threadPoolExecutor = new ThreadPoolExecutor(5,10,1000,
            TimeUnit.SECONDS,new ArrayBlockingQueue<>(1000),new ThreadPoolExecutor.AbortPolicy());

    public static void main(String[] args) {
        IntStream.range(0,10).forEach(i->{
            Future<Integer> submit = threadPoolExecutor.submit(new Task());
            try {
                System.out.println(submit.get(1030,TimeUnit.MILLISECONDS));
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            } catch (TimeoutException e) {
                e.printStackTrace();
            }
        });
        threadPoolExecutor.shutdown();
    }
}


class Task implements Callable<Integer>{

    @Override
    public Integer call() throws Exception {
        Thread.sleep(1000);
        return new Random().nextInt();
    }
}