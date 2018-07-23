package com.optimus.netty.handler;

import com.optimus.utils.FormatUtil;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import java.net.Socket;
import java.util.Date;
import java.util.Random;

/**
 * Created by Administrator on 2018/6/22.
 */
public class TimeClientHandler extends ChannelHandlerAdapter {

    private int count = 0;

    //@Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        ByteBuf m = (ByteBuf) msg; // (1)
        System.out.println("数据长度:"+m.writerIndex());
        Random random = new Random();
        int t = random.nextInt(100);
        long currentTimeMillis = (m.readUnsignedInt()) * 1000L;
        System.out.println("批次:"+t+",时间:"+FormatUtil.format(new Date(currentTimeMillis),"yyyy-MM-dd HH:mm:ss")+",次数:"+(++count));

    }

    //@Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("channelActive:"+ctx.channel());
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        //ctx.close();
    }
}
