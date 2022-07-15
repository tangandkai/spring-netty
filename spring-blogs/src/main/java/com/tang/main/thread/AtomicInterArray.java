package com.tang.main.thread;

import java.util.concurrent.atomic.AtomicIntegerArray;

public class AtomicInterArray {

    private static final AtomicIntegerArray array = new AtomicIntegerArray(10);
    public static void main(String[] args) {
        int i=10;
        while (i>0){
            i--;
            new Thread(new Runnable(){
                @Override
                public void run() {
                    System.out.println(array.getAndAdd(0,2));
                }
            }).start();
        }
    }
}
