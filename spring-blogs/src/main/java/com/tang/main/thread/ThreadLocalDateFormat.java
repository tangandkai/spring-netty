package com.tang.main.thread;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.*;
import java.util.stream.IntStream;

public class ThreadLocalDateFormat {

    private static final ExecutorService pool = new ThreadPoolExecutor(5,10,1000,
            TimeUnit.SECONDS,new ArrayBlockingQueue<>(1000),new ThreadPoolExecutor.AbortPolicy());
    private static ThreadLocal<Integer> threadLocal = new ThreadLocal<>();

    public static void main(String[] args) {
        threadLocal.set(100);
        IntStream.range(0,100).forEach((i)->{
            pool.submit(()->{
                String date = new ThreadLocalDateFormat().date(i);
//                System.out.println(date);
//                threadLocal.set(new User(i,"jack"+i));
//                User user = threadLocal.get();
                threadLocal.set(i);
            });
        });
        Integer integer = threadLocal.get();
        System.out.println(integer);
        pool.shutdown();
    }

    public String date(int seconds){
        Date date = new Date(1000 * seconds);
        SimpleDateFormat dateFormat = ThreadSafeFormatter.dateFormatThreadLocal.get();
        return dateFormat.format(date);
    }
}

class ThreadSafeFormatter {
    public static ThreadLocal<SimpleDateFormat> dateFormatThreadLocal = new ThreadLocal<SimpleDateFormat>() {
        @Override
        protected SimpleDateFormat initialValue() {
            return new SimpleDateFormat("mm:ss");
        }
    };
}

class User{
    private Integer id;
    private String name = "jack";

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public User(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}