package com.tang.netty.websocket;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.*;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.stream.ChunkedWriteHandler;
import io.netty.util.CharsetUtil;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;

/**
 * 构建hwebsocket服务器
 * @author tangwenkai
 * @date 2021-05-26 16:32 2021-05-26 20:52
 */
public class WebSocketServer {

    /**
     * 服务器端口
     */
    private int PORT = 8080;

    /**
     * 构造
     * @param port
     */
    public WebSocketServer(int port){
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
                            //因为基于http协议，使用http的编解码器
                            .addLast("codec",new HttpServerCodec())
                            //是以块方式写，添加ChunkedWriteHandler处理器
                            .addLast("chunkHandler",new ChunkedWriteHandler())
                            .addLast("compressor",new HttpContentCompressor())
                            //http数据在传输过程中是分段,HttpObjectAggregator就是将多个段聚合
                            //这就是为什么，当浏览器发送大量数据时，就会发出多次http请求
                            .addLast("aggregator",new HttpObjectAggregator(65536))
                            //对应websocket，他的数据是以帧形式传递，可以看到WebSocketFrame下面有六个子类
                            //浏览器请求时，ws://localhost:8080/xxx (hello)
                            //WebSocketServerProtocolHandler核心功能将http协议升级为ws协议，保持长连接
                            .addLast("protocol",new WebSocketServerProtocolHandler("/hello"))
                            .addLast("handler0",new WebSocketFrameHandler());
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
        new WebSocketServer(8080).start();
    }
}

class WebSocketFrameHandler extends SimpleChannelInboundHandler<TextWebSocketFrame>{

    /**
     *
     * @param ctx
     * @param msg
     * @throws Exception
     */
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, TextWebSocketFrame msg) throws Exception {
        System.out.println("服务端收到消息: "+msg.text());
        //回复
        ctx.channel().writeAndFlush(new TextWebSocketFrame("服务器时间 "+ LocalDateTime.now()+" : "+msg.text()));
    }

    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        //id 表示唯一值
        System.out.println("handlerAdded 被调用 "+ctx.channel().id().asLongText());
    }

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        System.out.println("handlerRemoved 被调用"+ctx.channel().id().asLongText());
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        System.out.println("异常发生 "+ cause.getMessage());
        ctx.channel().close();
    }
}