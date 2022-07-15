package com.tang.netty.commucation;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.timeout.IdleStateEvent;

/**
 * 自定义idleHandler处理器
 * @author tangwenkai
 * @date 2021-06-04 20:33
 */
public class idleHandler extends ChannelInboundHandlerAdapter {

    /**
     *
     * @param ctx
     * @param evt 事件
     * @throws Exception
     */
    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        if (evt instanceof IdleStateEvent){
            //转型为IdleStateEvent
            IdleStateEvent stateEvent= (IdleStateEvent) evt;
            String eventType = null;
            switch (stateEvent.state()){
                case READER_IDLE:
                    eventType = "读空闲";
                    break;
                case WRITER_IDLE:
                    eventType = "写空闲";
                    break;
                case ALL_IDLE:
                    eventType = "都写空闲";
                    break;
            }
            System.out.println(ctx.channel().remoteAddress()+" --超时事件--"+eventType);
            System.out.println("服务器做相应处理.........");
            ctx.channel().close();
        }
    }
}
