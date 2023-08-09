package com.tang.es.config;

import com.alibaba.fastjson2.JSONObject;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class EsPropertiesTest {

    @Autowired
    private EsProperties esProperties;

    @Test
    void test(){

        String string = JSONObject.toJSONString(esProperties);
        System.out.println(string);
    }
}