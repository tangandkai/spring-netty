package com.tang.boot.beans.jackson;

import java.io.Serializable;

public class RequestBasic implements Serializable {
    private Header header = new RequestBasic.Header();

    public RequestBasic(){

    }

    public Header getHeader() {
        return header;
    }

    public void setHeader(Header header) {
        this.header = header;
    }

    public class Header implements Serializable{
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
