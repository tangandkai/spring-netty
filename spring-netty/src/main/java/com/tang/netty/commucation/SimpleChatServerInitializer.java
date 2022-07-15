package com.tang.netty.commucation;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.Delimiters;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.handler.timeout.IdleStateHandler;

import java.util.concurrent.TimeUnit;

public class SimpleChatServerInitializer extends ChannelInitializer<SocketChannel> {
    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();
        pipeline.addLast("framer",new DelimiterBasedFrameDecoder(8192, Delimiters.lineDelimiter()));
        pipeline.addLast("decoder", new StringDecoder());
        pipeline.addLast("encoder", new StringEncoder());
        //IdleStateHandler netty提供的心跳检测处理
        // long readerIdleTime: 表示多长时间没有读，就会发送一个心跳检测包检测是否连接
        // long writerIdleTime: 表示多长时间没有写，就会发送一个心跳检测包检测是否连接
        // long allIdleTime: 表示多长时间没有读写，就会发送一个心跳检测包检测是否连接
        //当IdleStateHandler出触发后，就会传递给管道的下一个handler取处理，通过调用下一个handler的userEventatriggered
        //在该方法中处理IdleStateHandler（读空闲，写空闲，读写空闲）
        pipeline.addLast("idel",new IdleStateHandler(10,30,50, TimeUnit.SECONDS));
        pipeline.addLast("idelHandler",new idleHandler());
        pipeline.addLast("handler", new SimpleChatServerHandler());
        System.out.println("SimpleChatClient:"+ch.remoteAddress() +"连接上");
    }
}
