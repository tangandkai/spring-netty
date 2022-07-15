package com.tang.boot.beans;

import io.swagger.annotations.ApiParam;

/**
 * 用户
 * @author tangwenkai
 * @date 2021-06-03 12:42
 */
public class Users {

    @ApiParam("用户名")
    private String name;

    @ApiParam("年龄")
    private int age;

    @ApiParam("地址")
    private String address;

    @ApiParam("号码")
    private String phone;

    public Users() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Override
    public String toString() {
        return "Users{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", address='" + address + '\'' +
                ", phone='" + phone + '\'' +
                '}';
    }
}
