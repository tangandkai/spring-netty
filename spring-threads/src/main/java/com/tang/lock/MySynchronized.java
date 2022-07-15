package com.tang.lock;

public class MySynchronized {

    public static void main(String[] args) {

        for (int i=0;i<10;i++){
            new Thread(()->{
               test();
            }).start();
        }
    }

    public synchronized static void test_1(){
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(Thread.currentThread().getName()+"get the lock");
    }
    public static void test(){
        synchronized (Lock.class){
            try {
                synchronized (Lock.class){
                    System.out.println(Thread.currentThread().getName()+"get the lock again");
                }
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread().getName()+"get the lock");
        }
    }
}


class Lock{

}