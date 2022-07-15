package com.tang.queue;

import java.util.Iterator;
import java.util.concurrent.*;

public class DelayQueueT {

    public static void main(String[] args) throws InterruptedException {
        ThreadPoolExecutor poolExecutor = new ThreadPoolExecutor(5,
                10,
                5,
                TimeUnit.SECONDS,
                new LinkedBlockingQueue<Runnable>(100),
                new ThreadPoolExecutor.DiscardPolicy());

        BlockingQueue delayed =  new DelayQueue<DelayedDTO>();

        poolExecutor.execute(()->{
            try {
                System.out.println(Thread.currentThread().getName()+" begin put");
                long beginTime = System.currentTimeMillis();
                delayed.put(new DelayedDTO(System.currentTimeMillis() + 2000L,beginTime));//延迟 2 秒执行
                delayed.put(new DelayedDTO(System.currentTimeMillis() + 5000L,beginTime));//延迟 5 秒执行
                delayed.put(new DelayedDTO(System.currentTimeMillis() + 1000L * 10,beginTime));//延迟 10 秒执行
                System.out.println(Thread.currentThread().getName()+" end put");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        Thread.sleep(10);
        poolExecutor.execute(()->{
            try {
                System.out.println(Thread.currentThread().getName()+" begin consumer");
                long beginTime = System.currentTimeMillis();
                int size;
                while ((size = delayed.size())>0){
                    DelayedDTO take = (DelayedDTO)delayed.take();
                    take.run();
                }
                System.out.println(Thread.currentThread().getName()+" end consumer");
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        poolExecutor.shutdown();
    }

    static class DelayedDTO implements Delayed{

        private Long s;
        private Long beginTime;

        public Long getS() {
            return s;
        }

        public void setS(Long s) {
            this.s = s;
        }

        public Long getBeginTime() {
            return beginTime;
        }

        public void setBeginTime(Long beginTime) {
            this.beginTime = beginTime;
        }

        public DelayedDTO(Long s,Long beginTime){
            this.s = s;
            this.beginTime = beginTime;
        }

        @Override
        public long getDelay(TimeUnit unit) {
            return unit.convert(s - System.currentTimeMillis(), TimeUnit.MILLISECONDS);
        }

        @Override
        public int compareTo(Delayed o) {
            return (int) (this.getDelay(TimeUnit.MILLISECONDS) - o.getDelay(TimeUnit.MILLISECONDS));
        }

        public void run(){
            System.out.println("现在已经过了 "+(System.currentTimeMillis() - beginTime)/1000+" 秒钟");
        }
    }
}
