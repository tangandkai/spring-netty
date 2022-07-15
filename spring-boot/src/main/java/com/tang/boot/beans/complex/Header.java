package com.tang.boot.beans.complex;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class Header {

    @JsonAlias("uuid")
    private String uuid;
    @JsonProperty("service_name")
    private String serviceName;
    @JsonAlias("app_key")
    private String appkey;

    public Header(){

    }

    public Header(String uuid,String serviceName,String appkey){
        this.uuid = uuid;
        this.serviceName = serviceName;
        this.appkey = appkey;
    }
}
