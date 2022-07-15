package com.tang.netty.dubborpc.provider;

import com.tang.netty.dubborpc.netty.NettyServer;

public class ServerBootstrap {

    public static void main(String[] args) {
        NettyServer.startServer("127.0.0.1", 7000);
    }
}
