package com.tang.netty.codec;

import com.tang.netty.codec.protolbuf.MyDataInfoo;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.protobuf.ProtobufDecoder;
import io.netty.handler.codec.protobuf.ProtobufEncoder;

import static com.tang.netty.codec.MessageUtils.getMessage;
import static com.tang.netty.codec.MessageUtils.parseMessage;

/**
 * 构建http服务器
 * @author tangwenkai
 * @date 2021-05-26 16:32 2021-05-26 20:52
 */
public class NettyServer {

    /**
     * 服务器端口
     */
    private int PORT = 8080;

    /**
     * 构造
     * @param port
     */
    public NettyServer(int port){
        this.PORT = port;
    }

    /**
     * 启动
     */
    public void start(){
        NioEventLoopGroup boss = new NioEventLoopGroup();
        NioEventLoopGroup worker = new NioEventLoopGroup();
        try {
            ServerBootstrap bootstrap = new ServerBootstrap();
            bootstrap
                    .group(boss,worker)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                @Override
                protected void initChannel(SocketChannel ch) throws Exception {
                    ch.pipeline()
                            //指定对哪种对象解码
                            .addLast("Decoder",new ProtobufDecoder(MyDataInfoo.MyMessage.getDefaultInstance()))
                            .addLast("Encoder",new ProtobufEncoder())
                            .addLast("handler",new NettyServerHandler());
                }
            }).
                    option(ChannelOption.SO_BACKLOG,128).
                    childOption(ChannelOption.SO_KEEPALIVE,true);
            ChannelFuture future = bootstrap.bind(this.PORT).sync();
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
            System.out.println("HttpServer 关闭了");
        }
    }

    public static void main(String[] args) {
        new NettyServer(8080).start();
    }
}

class NettyServerHandler extends ChannelInboundHandlerAdapter{
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        //读取从客户端发送的student
        parseMessage(msg);
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.channel().writeAndFlush(getMessage());
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        System.out.println(cause.getMessage());
        ctx.channel().close();
    }
}