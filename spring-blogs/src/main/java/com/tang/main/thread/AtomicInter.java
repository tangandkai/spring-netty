package com.tang.main.thread;

import java.util.concurrent.atomic.AtomicInteger;

public class AtomicInter {

    private static final AtomicInteger integer = new AtomicInteger(0);
    public static void main(String[] args) {
        int i=10;
        while (i>0){
            i--;
            new Thread(new Runnable() {
                @Override
                public void run() {
                    System.out.println(integer.incrementAndGet());
                }
            }).start();
        }
//        System.out.println(integer.get());
    }
}
