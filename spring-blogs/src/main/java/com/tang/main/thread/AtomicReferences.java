package com.tang.main.thread;

import java.util.Arrays;
import java.util.concurrent.atomic.AtomicReference;

public class AtomicReferences {

    /**
     * 引用类型原子类
     * 可以让一个对象保证原子性
     */
    private static final AtomicReference<Student> REFERENCE = new AtomicReference();

    public static void main(String[] args) {
        REFERENCE.set(new Student(1,"jack"));
        int i=10;
        while (i>0){
            i--;
            new Thread(() -> {
                System.out.println(REFERENCE.get().getId());
            }).start();
        }
    }
}


class Student{

    private int id;
    private String name;

    public int getId() {
        try {
            Thread.sleep(1000);
            id++;
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Student(int id, String name) {
        this.id = id;
        this.name = name;
    }
}