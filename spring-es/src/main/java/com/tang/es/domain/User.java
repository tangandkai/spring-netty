package com.tang.es.domain;

import java.io.Serializable;

/**
 * 用户数据
 */
public class User implements Serializable {

    private String name;

    private String sex;

    private Long age;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public Long getAge() {
        return age;
    }

    public void setAge(Long age) {
        this.age = age;
    }
}
