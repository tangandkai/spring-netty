package com.tang.rpc.codec;

import com.tang.rpc.compress.Compressor;
import com.tang.rpc.factory.CompressorFactory;
import com.tang.rpc.protocol.Header;
import com.tang.rpc.protocol.Message;
import com.tang.rpc.protocol.Request;
import com.tang.rpc.protocol.Response;
import com.tang.rpc.serialization.Serialization;
import com.tang.rpc.factory.SerializationFactory;
import com.tang.rpc.utils.Constants;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

/**
 * 自定义解码器
 * 通过继承ByteToMessageDecoder，实现
 * @author tangwenkai
 * @date 2021-05-26 15:27
 */
public class RpcDecoder extends ByteToMessageDecoder {
    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf byteBuf, List<Object> out) throws Exception {
        if (byteBuf.readableBytes()< Constants.HEADER_SIZE){
            return;//不到16字节的话无法解析消息头，暂不读取
        }
        // 记录当前readIndex指针的位置，方便重置,可以通过resetReaderIndex()回到标记点
        byteBuf.markReaderIndex();
        // 尝试读取消息头的魔数部分
        short magic = byteBuf.readShort();
        if (magic!=Constants.MAGIC){// 魔数不匹配会抛出异常
            byteBuf.resetReaderIndex();//重置readIndex指针
            throw new RuntimeException("magic number error: "+magic);
        }
        //依次读取消息版本、附加信息、消息ID以及消息体长度四部分
        byte version = byteBuf.readByte();
        byte extraInfo = byteBuf.readByte();
        long messageId = byteBuf.readLong();
        int size = byteBuf.readInt();
        Object body = null;
        // 心跳消息是没有消息体的，无需读取
        if (!Constants.isHeartBeat(extraInfo)) {
            // 对于非心跳消息，没有积累到足够的数据是无法进行反序列化的
            if (byteBuf.readableBytes() < size) {
                byteBuf.resetReaderIndex();
                return;
            }
            // 读取消息体并进行反序列化
            byte[] payload = new byte[size];
            byteBuf.readBytes(payload);
            // 这里根据消息头中的extraInfo部分选择相应的序列化和压缩方式
            Serialization serialization = SerializationFactory.get(extraInfo);
            Compressor compressor = CompressorFactory.get(extraInfo);
            if (Constants.isRequest(extraInfo)) {
                // 得到消息体
                body = serialization.deSerialize(compressor.unCompress(payload),Request.class);
            } else {
                // 得到消息体
                body = serialization.deSerialize(compressor.unCompress(payload),Response.class);
            }
        }
        // 将上面读取到的消息头和消息体拼装成完整的Message并向后传递
        Header header = new Header(magic, version, extraInfo, messageId, size);
        Message message = new Message(header, body);
        out.add(message);
    }
}
