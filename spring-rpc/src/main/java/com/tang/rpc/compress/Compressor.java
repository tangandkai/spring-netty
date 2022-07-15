package com.tang.rpc.compress;

import java.io.IOException;

/**
 * 解压缩接口定义
 * @author tangwenkai
 * @date 2021-05-26 00:53 2021-05-26 00:53
 */
public interface Compressor {

    /**
     * 压缩
     * @param array
     * @return
     * @throws IOException
     */
    byte[] compress(byte[] array) throws IOException;

    /**
     * 解压
     * @param array
     * @return
     * @throws IOException
     */
    byte[] unCompress(byte[] array) throws IOException;
}
