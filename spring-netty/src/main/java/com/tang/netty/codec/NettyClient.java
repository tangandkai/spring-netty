package com.tang.netty.codec;

import com.tang.netty.codec.protolbuf.MyDataInfoo;
import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.protobuf.ProtobufDecoder;
import io.netty.handler.codec.protobuf.ProtobufEncoder;

import static com.tang.netty.codec.MessageUtils.getMessage;
import static com.tang.netty.codec.MessageUtils.parseMessage;

/**
 * http客户端
 * @author tangwenkai
 * @date 2021-05-27 19:44
 */
public class NettyClient {

    /**
     * 端口
     */
    private int PORT;

    /**
     * 地址
     */
    private String HOST;

    public NettyClient(String host,int port) {
        HOST = host;
        PORT = port;
    }

    /**
     * 客户端启动
     */
    public void start() throws Exception {
        EventLoopGroup group = new NioEventLoopGroup();
        try {
            Bootstrap bootstrap = new Bootstrap();
            bootstrap
                    .group(group)
                    .channel(NioSocketChannel.class)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ch
                                    .pipeline()
                                    //加入 ProtoBufEncoder
                                    .addLast("Encoder",new ProtobufEncoder())
                                    .addLast("Decoder",new ProtobufDecoder(MyDataInfoo.MyMessage.getDefaultInstance()))
                                    .addLast("handler",new NettyClientHandler());
                        }
                    })
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

    public static void main(String[] args) throws Exception {
        new NettyClient("localhost",8080).start();
    }
}


class NettyClientHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        //发送一个message到服务器
        ctx.channel().writeAndFlush(getMessage());
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        //读取从服务端发送的数据
        parseMessage(msg);
        ByteBuf m = (ByteBuf)msg;
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.channel().eventLoop().execute(()->{
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            ctx.channel().writeAndFlush(getMessage());
        });
    }
}