package com.tang.rpc.compress;

import org.xerial.snappy.Snappy;

import java.io.IOException;

/**
 * 使用snappy解压缩
 * @date 2021-05-26 00:55
 * @author tangwenkai
 */
public class SnappyCompressor implements Compressor{

    /**
     * snappy压缩
     * @param array
     * @return
     * @throws IOException
     */
    @Override
    public byte[] compress(byte[] array) throws IOException {
        if (array==null){
            return null;
        }
        return Snappy.compress(array);
    }

    /**
     * snappy解压
     * @param array
     * @return
     * @throws IOException
     */
    @Override
    public byte[] unCompress(byte[] array) throws IOException {
        if (array==null){
            return null;
        }
        return Snappy.uncompress(array);
    }
}
