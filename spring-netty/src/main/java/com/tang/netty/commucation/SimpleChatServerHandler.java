package com.tang.netty.commucation;

import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.handler.timeout.IdleStateEvent;
import io.netty.util.CharsetUtil;
import io.netty.util.concurrent.GlobalEventExecutor;

import java.util.concurrent.TimeUnit;

public class SimpleChatServerHandler extends SimpleChannelInboundHandler<String> {

    public static ChannelGroup channels = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);

    /**
     * 通道连接时调用
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        Channel incoming = ctx.channel();

        System.out.println("simpleChatClient:"+incoming.remoteAddress()+"在线");
    }

    /**
     * 通道断开时调用
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        Channel incoming = ctx.channel();
        System.out.println("simpleChatClient:"+incoming.remoteAddress()+"掉线");
    }

    /**
     * 通道连接异常时被调用
     * @param ctx
     * @param cause
     * @throws Exception
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        Channel incoming = ctx.channel();
        System.out.println("simpleChatClient:"+incoming.remoteAddress()+"异常");
        ctx.close();
    }

    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        Channel incoming = ctx.channel();
        //广播一个消息给所有连接的通道
        channels.writeAndFlush("[SERVER] - "+incoming.remoteAddress()+" 加入\n");
        channels.add(incoming);
    }

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        Channel incoming = ctx.channel();
        //广播一个消息给所有连接的通道、
        Unpooled.copiedBuffer("hello word", CharsetUtil.UTF_8);
        channels.writeAndFlush("[SERVER] - "+incoming.remoteAddress()+" 离开\n");
//        channels.remove(incoming);
    }

    @Override
    protected void channelRead0(final ChannelHandlerContext ctx, final String msg) throws Exception {
        //使用异步方式执行
        ctx.channel().eventLoop().execute(new Runnable() {
            final Channel incoming = ctx.channel();
            @Override
            public void run() {
                for (Channel channel:channels){
                    if (channel!=incoming){//别人窗口看到
                        channel.writeAndFlush("["+incoming.remoteAddress()+"] "+msg+"\n");
                    }else {//自己窗口看到
                        channel.writeAndFlush("[you] "+msg+"\n");
                    }
                }
            }
        });
        //用户自定义普通任务,不会阻塞在这，影响正常执行。任务会被提交到task队列
        ctx.channel().eventLoop().execute(new Runnable() {
            @Override
            public void run() {

            }
        });
        //用户自定义定时任务，该任务是提交到scheduleTaskQueue中
        ctx.channel().eventLoop().schedule(new Runnable() {
            final Channel incoming = ctx.channel();
            @Override
            public void run() {
                for (Channel channel:channels){
                    if (channel!=incoming){//别人窗口看到
                        channel.writeAndFlush("["+incoming.remoteAddress()+"] "+msg+"\n");
                    }else {//自己窗口看到
                        channel.writeAndFlush("[you] "+msg+"\n");
                    }
                }
            }
        },3, TimeUnit.SECONDS);
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
//        super.channelReadComplete(ctx);
        System.out.println("读完..............");
    }


}
