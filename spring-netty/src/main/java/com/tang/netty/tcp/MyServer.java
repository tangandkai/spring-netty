package com.tang.netty.tcp;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.ByteToMessageDecoder;
import io.netty.util.CharsetUtil;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.UUID;

public class MyServer {

    private static int PORT = 8080;
    public static void main(String[] args) {
        NioEventLoopGroup boss = new NioEventLoopGroup();
        NioEventLoopGroup worker = new NioEventLoopGroup();
        try {
            ServerBootstrap bootstrap = new ServerBootstrap();
            bootstrap
                    .group(boss,worker)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new MyServerInitializer())
                    .option(ChannelOption.SO_BACKLOG,128)
                    .childOption(ChannelOption.SO_KEEPALIVE,true);
            ChannelFuture future = bootstrap.bind(PORT).sync();
            //给f注册监听器
            future.addListener(new ChannelFutureListener() {
                @Override
                public void operationComplete(ChannelFuture future) throws Exception {
                    if (future.isSuccess()){
                        System.out.println("监听端口8080成功....");
                    }
                }
            });
            future.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            worker.shutdownGracefully();
            boss.shutdownGracefully();
            System.out.println("Server 关闭了");
        }
    }
}

class MyServerInitializer extends ChannelInitializer<SocketChannel>{

    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        {
            ChannelPipeline pipeline = ch.pipeline();
            pipeline.addLast("decoder",new MyMessageDecoder());
            pipeline.addLast("codec",new MyMessageEncoder());
            pipeline.addLast("handler",new MyServerHandler());
        }
    }
}

class MyServerHandler extends SimpleChannelInboundHandler<MessageProtocol>{

    private int count;

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, MessageProtocol msg) throws Exception {
        int len = msg.getLen();
        byte[] content = msg.getContent();
        System.out.println("服务端接收到消息如下: ");
        System.out.println("长度: "+ len+"\t"+"内容: "+new String(content, CharsetUtil.UTF_8));
        System.out.println("服务器接收到的消息包数量: "+(++count));
        //回复消息
        String response = UUID.randomUUID().toString();
        byte[] bytes = response.getBytes(StandardCharsets.UTF_8);
        int resLen = bytes.length;
        //构建一个协议包
        MessageProtocol protocol = new MessageProtocol();
        protocol.setLen(resLen);
        protocol.setContent(bytes);
        //发送消息
        ctx.channel().writeAndFlush(protocol);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        System.out.println(cause.getMessage());
        ctx.channel().closeFuture();
    }
}