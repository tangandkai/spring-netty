package com.tang.boot.beans.jackson;

import java.io.Serializable;

public class RequestBasic1 implements Serializable {
    private Header header = new RequestBasic1.Header();

    public RequestBasic1(){

    }

    public Header getHeader() {
        return header;
    }

    public void setHeader(Header header) {
        this.header = header;
    }

    public static class Header implements Serializable{
        private String code;
        public Header(){
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getCode() {
            return code;
        }
    }
}
