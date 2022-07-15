package com.tang.netty.websocket;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.http.*;
import io.netty.util.CharsetUtil;

import java.net.URI;
import java.nio.charset.StandardCharsets;

/**
 * http客户端
 * @author tangwenkai
 * @date 2021-05-27 19:44
 */
public class WebSocketClient {

    /**
     * 端口
     */
    private int PORT;

    /**
     * 地址
     */
    private String HOST;

    public WebSocketClient(String host,int port) {
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
                                    .addLast(new HttpResponseDecoder())
                                    .addLast(new HttpRequestEncoder())
                                    .addLast(new HttpClientHandler());
                        }
                    })
                    .option(ChannelOption.SO_KEEPALIVE, true);
            ChannelFuture future = bootstrap.connect(HOST, PORT).sync();
            future.addListener(new ChannelFutureListener() {
                @Override
                public void operationComplete(ChannelFuture future) throws Exception {
                    if (future.isSuccess()){
                        System.out.println("连接服务器成功....");
                        URI uri = new URI("http://localhost:8080");
                        String content = "nice to meet you.....";
                        DefaultFullHttpRequest httpRequest = new DefaultFullHttpRequest(HttpVersion.HTTP_1_1,
                                HttpMethod.GET,
                                uri.toString(),
                                Unpooled.wrappedBuffer(content.getBytes(StandardCharsets.UTF_8)));
                        future.channel().writeAndFlush(httpRequest);
                    }
                }
            });
            future.channel().closeFuture().sync();
        } finally {
            group.shutdownGracefully();
        }
    }

    public static void main(String[] args) throws Exception {
        new WebSocketClient("localhost",8080).start();
    }
}


class HttpClientHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        if (msg instanceof HttpContent){
            HttpContent content = (HttpContent)msg;
            System.out.println(content.content().toString(CharsetUtil.UTF_8));
            content.release();
        }
    }
}