package com.tang.rpc.factory;

import com.tang.rpc.serialization.HessianSerialization;
import com.tang.rpc.serialization.Serialization;

/**
 *
 * @author tangwenkai
 * @date 2021-05-26 15:48
 */
public class SerializationFactory {

    public static Serialization get(byte type) {
        switch (type & 0x7) {
            case 0x0:
                return new HessianSerialization();
            default:
                return new HessianSerialization();
        }

    }
}
