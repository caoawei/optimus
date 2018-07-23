package com.optimus.netty.handler;

import com.optimus.utils.FormatUtil;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import java.util.Date;

/**
 * Created by Administrator on 2018/6/22.
 */
public class TimeHandler extends ChannelHandlerAdapter {

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }

    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        //cf.addListener(ChannelFutureListener.CLOSE);
        System.out.println("channelActive():"+ctx.channel());
        for (int i=0;i<100;i++) {
            long t = System.currentTimeMillis()/1000;
            ByteBuf time = ctx.alloc().buffer(4);
            time.writeInt((int) t);
            ctx.writeAndFlush(time);
        }
    }

    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        System.out.println("channelRead():"+ctx.channel());
    }


}
