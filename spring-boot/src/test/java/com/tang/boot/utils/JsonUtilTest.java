package com.tang.boot.utils;

import com.tang.boot.beans.jackson.Request;
import com.tang.boot.beans.jackson.Request1;
import com.tang.boot.beans.jackson.RequestBasic;
import org.junit.jupiter.api.Test;

class JsonUtilTest {

    @Test
    void parseObject() {
        RequestBasic request = new RequestBasic();
        Request.Header header = request.getHeader();
        header.setCode("200");
        String req = JsonUtil.toJsonString(request);
        RequestBasic request1 = JsonUtil.parseObject(req, RequestBasic.class);
        System.out.println(JsonUtil.toJsonString(request1));
    }

    @Test
    void parseObject1() {
        Request1 request = new Request1();
        Request1.Header header = request.getHeader();
        header.setCode("200");
        String req = JsonUtil.toJsonString(request);
        Request1 request1 = JsonUtil.parseObject(req, Request1.class);
        System.out.println(JsonUtil.toJsonString(request1));
    }

    @Test
    void reflect() throws ClassNotFoundException, InstantiationException, IllegalAccessException {
        Class<?> request = Class.forName("com.tang.boot.beans.jackson.Request");
        Object newInstance = request.newInstance();
        System.out.println(newInstance);
        if (newInstance instanceof Request){
            Request request1 = (Request) newInstance;
            request1.setName("request");
            request1.getHeader().setCode("200");
            System.out.println(JsonUtil.toJsonString(request1));
//            JsonUtil.parseObject(JsonUtil.toJsonString(request1),newInstance.getClass());
        }
    }
}