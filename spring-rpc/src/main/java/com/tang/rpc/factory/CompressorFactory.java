package com.tang.rpc.factory;

import com.tang.rpc.compress.Compressor;
import com.tang.rpc.compress.SnappyCompressor;

/**
 *
 * @author tangwenkai
 * @date 2021-05-26 15:49
 */
public class CompressorFactory {

    public static Compressor get(byte extraInfo) {
        switch (extraInfo & 24) {
            case 0x0:
                return new SnappyCompressor();
            default:
                return new SnappyCompressor();
        }
    }
}
