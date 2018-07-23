package com.optimus.netty.handler;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.util.ReferenceCountUtil;

/**
 * Created by Administrator on 2018/6/22.
 */
public class DiscardServerHandler extends ChannelHandlerAdapter {

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }

    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        try {

        } finally {
            ReferenceCountUtil.release(msg);
        }
    }
}
