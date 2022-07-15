package com.tang.netty;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.util.ByteProcessor;

import java.nio.charset.Charset;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        ByteBuf byteBuf = Unpooled.copiedBuffer("hello,word!", Charset.forName("utf-8"));
        byte[] bytes = byteBuf.array();
        System.out.println(new String(bytes,Charset.forName("utf-8")));
        byteBuf.forEachByte((value)-> {
                System.out.println((char) value);
                return true;
        });
        System.out.println(byteBuf.readerIndex());
    }
}
