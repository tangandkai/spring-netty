package com.tang.netty.dubborpc.publicinterface.impl;

import com.tang.netty.dubborpc.publicinterface.HelloService;

public class HelloServiceImpl implements HelloService {
    @Override
    public String hello(String mes) {
        return "hello "+mes;
    }
}
