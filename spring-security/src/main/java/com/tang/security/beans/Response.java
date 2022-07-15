package com.tang.security.beans;

import java.io.Serializable;

public class Response implements Serializable {

    private WebStatus webStatus;
    private Object message;

    public Response(){}

    public Response(WebStatus webStatus,Object message){
        this.webStatus = webStatus;
        this.message = message;
    }

    public void setWebStatus(WebStatus webStatus){
        this.webStatus = webStatus;
    }

    public void setMessage(Object message){
        this.message = message;
    }

    public WebStatus getWebStatus(){
        return webStatus;
    }

    public Object getMessage(){
        return message;
    }
}
