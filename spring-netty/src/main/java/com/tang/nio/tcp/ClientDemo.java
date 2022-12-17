package com.tang.nio.tcp;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;

public class ClientDemo {

    public static void main(String[] args) throws IOException, InterruptedException {
        SocketChannel socketChannel = SocketChannel.open();
        socketChannel.connect(new InetSocketAddress(8888));

        while (true){
            ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
            byteBuffer.put("nice to meet you!".getBytes(StandardCharsets.UTF_8));
            byteBuffer.flip();
            socketChannel.write(byteBuffer);
            Thread.sleep(1*1000);
            byteBuffer.flip();
            socketChannel.read(byteBuffer);
            byte[] array = byteBuffer.array();
            System.out.println(new String(array));
            return;
        }
    }
}
