package com.tang.security.beans;

public enum WebStatus {

    OK("成功",200),
    FAILED("失败",300);

    private String status;
    private int code;

    private WebStatus(String status,int code){
        this.status = status;
        this.code = code;
    }

    public String getStatus(){
        return this.status;
    }

    public int getCode(){
        return this.code;
    }
}
