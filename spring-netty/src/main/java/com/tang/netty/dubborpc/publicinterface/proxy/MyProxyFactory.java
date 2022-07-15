package com.tang.netty.dubborpc.publicinterface.proxy;

import java.lang.reflect.Proxy;

public class MyProxyFactory {

    public static Object getProxy(Object target){
        MyInvocationHandler handler = new MyInvocationHandler();
        handler.setTarget(target);
        return Proxy.newProxyInstance(target.getClass().getClassLoader(),
                target.getClass().getInterfaces(),
                handler);
    }
}
