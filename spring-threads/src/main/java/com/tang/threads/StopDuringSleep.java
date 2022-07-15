package com.tang.threads;

/**
 * 中断休眠期间的线程
 * @author tangwenkai
 * @date 2021-05-26 16:32 2021-05-26 17:40
 */
public class StopDuringSleep {

    public static void main(String[] args) throws InterruptedException {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                int num = 0;
                while (!Thread.currentThread().isInterrupted()&&num<100){
                    System.out.println(num);
                    num++;
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                        e.printStackTrace();
                    }
                }
            }
        };
        Thread thread = new Thread(runnable);
        thread.start();
        Thread.sleep(2000);
        thread.interrupt();
        System.out.println("thread interupt.....");
    }
}
