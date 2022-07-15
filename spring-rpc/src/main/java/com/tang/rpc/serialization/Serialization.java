package com.tang.rpc.serialization;

import java.io.IOException;

/**
 * 自定义序列化接口
 * @date 2021-05-26 00:54
 * @author tangwenkai
 */
public interface Serialization {

    /**
     * 序列化
     * @param obj
     * @param <T>
     * @return
     * @throws IOException
     */
    <T> byte[] serialize(T obj)throws IOException;

    /**
     * 反序列化
     * @param data
     * @param clz
     * @param <T>
     * @return
     * @throws IOException
     */
    <T> T deSerialize(byte[] data, Class<T> clz)throws IOException;
}
