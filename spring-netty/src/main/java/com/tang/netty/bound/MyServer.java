package com.tang.netty.bound;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

public class MyServer {

    private static int PORT = 8080;
    public static void main(String[] args) {
        NioEventLoopGroup boss = new NioEventLoopGroup(1);
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
            pipeline.addLast("decoder",new MyByteToLongDecoder());
            pipeline.addLast("handler",new MyServerHandler());
        }
    }
}

class MyByteToLongDecoder extends ByteToMessageDecoder{

    /**
     *
     * @param ctx
     * @param in 入站的ByteBuf
     * @param out
     * @throws Exception
     */
    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        System.out.println("MyByteToLongDecoder decode 被调用");
        //需要判断有八个字节才能读取
        if (in.readableBytes()>=8){
            out.add(in.readLong());
        }
    }
}

class MyServerHandler extends SimpleChannelInboundHandler<Long>{

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Long msg) throws Exception {
        System.out.println("服务端读取到数据: "+msg);
        System.out.println("服务器处理线程: "+Thread.currentThread().getName());

        ctx.channel().eventLoop().execute(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(10*1000);
                    System.out.println("服务器异步处理线程: "+Thread.currentThread().getName());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        System.out.println("处理完毕........");
    }
}