package com.tang.netty.http;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.*;
import io.netty.util.CharsetUtil;

import java.nio.charset.StandardCharsets;

/**
 * 构建http服务器
 * @author tangwenkai
 * @date 2021-05-26 16:32 2021-05-26 20:52
 */
public class HttpServer {

    /**
     * 服务器端口
     */
    private int PORT = 8080;

    /**
     * 构造
     * @param port
     */
    public HttpServer(int port){
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
                            .addLast("codec",new HttpServerCodec())
                            .addLast("compressor",new HttpContentCompressor())
                            .addLast("aggregator",new HttpObjectAggregator(65536))
                            .addLast("handler0",new HttpServerHandler());
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
        new HttpServer(8080).start();
    }
}

class HttpServerHandler extends SimpleChannelInboundHandler<FullHttpRequest>{

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, FullHttpRequest msg) throws Exception {
        String content = String.format("Receive http request, uri: %s, method: %s, content: %s%n",
                msg.uri(), msg.method(), msg.content().toString(CharsetUtil.UTF_8));
        DefaultFullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1,
                HttpResponseStatus.OK,
                Unpooled.wrappedBuffer(content.getBytes(StandardCharsets.UTF_8)));
        ctx.writeAndFlush(response).addListener(ChannelFutureListener.CLOSE);
    }
}