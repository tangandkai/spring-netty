package com.tang.boot.services;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class sTest {

    @Resource
    private BeanT beanT;
    @Test
    void getBean() {
        beanT.getBean("userServiceImpl");
    }
}