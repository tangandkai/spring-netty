package com.tang.queue.DIY;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.ReentrantLock;

/**
 *
 * 自定义队列实现
 * @author tangwenkai
 * @date 2021-06-21 17:43 2021-06-21 17:50
 */
public class DIYQueue<T> implements Queue<T>{

    /**
     * 队列的大小，使用 AtomicInteger 来保证其线程安全
     */
    private AtomicInteger size = new AtomicInteger(0);

    /**
     * 容量
     */
    private final Integer capacity;

    /**
     * 放数据锁
     */
    private ReentrantLock putLock = new ReentrantLock();

    /**
     * 拿数据锁
     */
    private ReentrantLock takeLock = new ReentrantLock();

    /**
     * 队列头
     */
    private volatile Node<T> head;

    /**
     * 队列尾
     */
    private volatile Node<T> tail;

    /**
     * 自定义队列元素
     */
    class DIYNode extends Node<T>{
        public DIYNode(T item) {
            super(item);
        }
    }

    /**
     * 无参数构造器，默认最大容量是 Integer.MAX_VALUE
     */
    public DIYQueue(){
        capacity = Integer.MAX_VALUE;
        head = tail = new DIYNode(null);
    }

    /**
     * 有参数构造器，可以设定容量的大小
     * @param capacity
     */
    public DIYQueue(Integer capacity) {
        // 进行边界的校验
        if(null == capacity || capacity < 0){
            throw new IllegalArgumentException();
        }
        this.capacity = capacity;
        head = tail = new DIYNode(null);
    }

    @Override
    public boolean put(T item) {
        if (null==item){
            return false;
        }
        try {
            //尝试加锁，500 毫秒未获得锁直接被打断
            boolean b = putLock.tryLock(500, TimeUnit.MILLISECONDS);
            if (!b){
                return false;
            }
            if (size.get()>=capacity){
                System.out.println("queue is full");
                return false;
            }
            tail = tail.next = new DIYNode(item);
            size.incrementAndGet();
        }catch (InterruptedException e){
            e.printStackTrace();
        }finally {
            putLock.unlock();
        }
        return true;
    }

    @Override
    public T take() {
        //队列是空的，返回 null
        if (size.get()==0){
            return null;
        }
        try {
            boolean b = takeLock.tryLock(500, TimeUnit.MILLISECONDS);
            if (!b){
                throw new RuntimeException("加锁失败");
            }
            Node<T> tNode = head.next;
            T item = head.item;
            head.item = null;
            head = tNode;
            size.decrementAndGet();
            return item;
        }catch (InterruptedException e){
            e.printStackTrace();
        }finally {
            takeLock.unlock();
        }
        return null;
    }
}
