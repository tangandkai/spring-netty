package com.tang.boot.beans.jackson;

public class Request extends RequestBasic {

    private String name;
    public Request(){
        super();
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
