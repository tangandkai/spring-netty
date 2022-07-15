package com.tang.netty.commucation;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.channels.ClosedChannelException;

public class SimpleChatClient {
    private String host;
    private int port;

    public SimpleChatClient(String host,int port){
        this.host = host;
        this.port = port;
    }

    public void run(){
        EventLoopGroup group = new NioEventLoopGroup();
        try{
            Bootstrap boot = new Bootstrap();
            boot.group(group).
                    channel(NioSocketChannel.class).
                    handler(new SimpleChatClientInitializer());
            ChannelFuture future = boot.connect(this.host, this.port).sync();
            BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
            while (true && future.channel().isActive()){
                future.channel().writeAndFlush(in.readLine()+"\r\n");
            }
            future.channel().closeFuture();
        }catch (IOException | InterruptedException e) {
            e.printStackTrace();
        } finally {
            group.shutdownGracefully();
        }
    }

    public static void main(String[] args) {
        new SimpleChatClient("127.0.0.1",8080).run();
    }
}
