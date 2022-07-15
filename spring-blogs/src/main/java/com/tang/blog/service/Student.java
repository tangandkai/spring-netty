package com.tang.blog.service;

import org.springframework.stereotype.Component;

@Component
public class Student {

    private int age = 10;
    private String name = "jack";

    public int getAge() {
        System.out.println("get age: " +this.age);
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
