package com.tang.netty.tcp;


import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.MessageToByteEncoder;
import io.netty.handler.codec.ReplayingDecoder;
import io.netty.util.CharsetUtil;

import java.nio.charset.Charset;
import java.util.List;
import java.util.UUID;

public class MyClient {

    private static int PORT = 8080;
    private static String HOST = "localhost";
    public static void main(String[] args) throws InterruptedException {
        EventLoopGroup group = new NioEventLoopGroup();
        try {
            Bootstrap bootstrap = new Bootstrap();
            bootstrap
                    .group(group)
                    .channel(NioSocketChannel.class)
                    .handler(new MyClientInitializer())
                    .option(ChannelOption.SO_KEEPALIVE, true);
            ChannelFuture future = bootstrap.connect(HOST, PORT).sync();
            future.addListener(new ChannelFutureListener() {
                @Override
                public void operationComplete(ChannelFuture future) throws Exception {
                    if (future.isSuccess()){
                        System.out.println("连接服务器成功....");
                    }
                }
            });
            future.channel().closeFuture().sync();
        } finally {
            group.shutdownGracefully();
        }
    }
}

class MyClientInitializer extends ChannelInitializer<SocketChannel>{

    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();
        pipeline.addLast("encoder",new MyMessageEncoder());
        pipeline.addLast("codec",new MyMessageDecoder());
        pipeline.addLast("handler",new MyClientHandler());
    }
}

class MyClientHandler extends SimpleChannelInboundHandler<MessageProtocol>{

    private int count;
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        //发送十条数据
        for (int i=0;i<10;i++){
            String message = "今天天气冷，吃火锅";
            byte[] bytes = message.getBytes(Charset.forName("utf-8"));
            int len = bytes.length;

            //创建协议包
            MessageProtocol messageProtocol = new MessageProtocol();
            messageProtocol.setLen(len);
            messageProtocol.setContent(bytes);
            ctx.writeAndFlush(messageProtocol);
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        System.out.println("异常消息= "+cause.getMessage());
        ctx.channel().closeFuture();
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, MessageProtocol msg) throws Exception {
        int len = msg.getLen();
        byte[] content = msg.getContent();
        System.out.println("客户端接收到消息如下: ");
        System.out.println("长度: "+ len+"\t"+"内容: "+new String(content, CharsetUtil.UTF_8));
        System.out.println("客户端接收到的消息包数量: "+(++count));
    }
}