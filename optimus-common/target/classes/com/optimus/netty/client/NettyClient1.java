package com.optimus.netty.client;

import com.optimus.netty.decoder.TimeDecoder;
import com.optimus.netty.handler.TimeClientHandler;
import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

/**
 * Created by Administrator on 2018/6/22.
 */
public class NettyClient1 {

    public static void main(String[] args) {
        EventLoopGroup workerGroup = new NioEventLoopGroup();

        try {

            Bootstrap cl = new Bootstrap();
            cl.group(workerGroup);
            cl.channel(NioSocketChannel.class);
            cl.handler(new ChannelInitializer<SocketChannel>() {
                @Override
                protected void initChannel(SocketChannel ch) throws Exception {
                    ch.pipeline().addLast(new TimeDecoder(),new TimeClientHandler());
                }
            });

            cl.option(ChannelOption.SO_KEEPALIVE,true);

            ChannelFuture f = cl.connect("127.0.0.1", 9090).sync(); // (5)


            for (int i = 0;i<100;i++) {
                if(f.channel().isActive()){
                    Channel channel = f.channel();
                    ByteBuf ackData = channel.alloc().buffer(4);
                    ackData.writeInt(i);
                    channel.writeAndFlush(ackData);
                } else {
                    f.sync();
                }

                try {
                    Thread.sleep(1000);
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
            // Wait until the connection is closed.
            f.channel().closeFuture().sync();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            workerGroup.shutdownGracefully();
        }
    }
}
