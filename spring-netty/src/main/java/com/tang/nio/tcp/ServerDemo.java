package com.tang.nio.tcp;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

public class ServerDemo {

    public static void main(String[] args) throws IOException {
//        ServerSocket serverSocket = new ServerSocket();
//        serverSocket.bind(new InetSocketAddress(8888));
//        while (true){
//            Socket accept = serverSocket.accept();
//            SocketAddress remoteAddress = accept.getChannel().getRemoteAddress();
//            System.out.println(remoteAddress.toString());
//            InputStream inputStream = accept.getInputStream();
//
//        }

        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.bind(new InetSocketAddress(8888));
        while (true){
            SocketChannel socketChannel = serverSocketChannel.accept();
            ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
            socketChannel.read(byteBuffer);
            byte[] array = byteBuffer.array();
            System.out.println(new String(array));
            byteBuffer.flip();
            socketChannel.write(byteBuffer);
        }
    }
}
