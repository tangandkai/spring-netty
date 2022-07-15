package com.tang.netty.dubborpc.publicinterface.proxy;

import com.tang.netty.dubborpc.publicinterface.HelloService;
import com.tang.netty.dubborpc.publicinterface.impl.HelloServiceImpl;

public class ProxyTest {

    public static void main(String[] args) {

        HelloServiceImpl service = new HelloServiceImpl();
        HelloService helloService = (HelloService)MyProxyFactory.getProxy(service);
        String hello = helloService.hello("nice to meet you");
        System.out.println(hello);
    }
}
