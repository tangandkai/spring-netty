package com.tang.boot.beans.complex;

import java.util.UUID;

public class Params {

    private Header header;
    private Body body;

    public Header getHeader() {
        return header;
    }

    public void setHeader(Header header) {
        this.header = header;
    }

    public Body getBody() {
        return body;
    }

    public void setBody(Body body) {
        this.body = body;
    }

    @Override
    public String toString() {
        return "Params{" +
                "header=" + header +
                ", body=" + body +
                '}';
    }

    public static void main(String[] args) {
//        Params params = new Params();
//        Header header = new Header();
//        header.setAppkey("szgz");
//        header.setUuid(UUID.randomUUID().toString());
//        header.setServiceName("nice");
//        params.setHeader(header);
//
//        Body body = new Body();
//
//        Dimensions dimensions = new Dimensions();
//        dimensions.s
    }
}
