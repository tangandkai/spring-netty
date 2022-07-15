package com.tang.boot.beans.jackson;

public class Request1 extends RequestBasic1 {

    private String name;
    public Request1(){
        super();
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
