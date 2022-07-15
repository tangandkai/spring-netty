package com.tang.shard.mapper.entity;

public class HealthUser {

    Long userId;
    String userName;

    public Long getUserId() {
        return userId;
    }
    public void setUserId(Long userId) {
        this.userId = userId;
    }
    public String getUserName() {
        return userName;
    }
    public void setUserName(String userName) {
        this.userName = userName;
    }

    @Override
    public String toString() {
        return "HealthUser{" +
                "userId=" + userId +
                ", userName='" + userName + '\'' +
                '}';
    }
}
