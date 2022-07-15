package com.tang.rpc.protocol;

import com.tang.rpc.serialization.HessianSerialization;

/**
 * rpc协议消息体
 * @date 2021-05-26 00:52
 * @author tangwenkai
 */
public class Request extends HessianSerialization {

    /**
     * 请求的Service类名
     */
    private String serviceName;

    /**
     * 请求的方法名称
     */
    private String methodName; //

    /**
     * 请求方法的参数类型
     */
    private Class[] argTypes; //

    /**
     * 请求方法的参数
     */
    private Object[] args;

    public Request(String serviceName, String methodName, Object[] args) {
        this.serviceName = serviceName;
        this.methodName = methodName;
        this.args = args;
        this.argTypes = new Class[args.length];
        for (int i = 0; i < args.length; i++) {
            argTypes[i] = args[i].getClass();
        }
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public Class[] getArgTypes() {
        return argTypes;
    }

    public void setArgTypes(Class[] argTypes) {
        this.argTypes = argTypes;
    }

    public Object[] getArgs() {
        return args;
    }

    public void setArgs(Object[] args) {
        this.args = args;
    }
}
