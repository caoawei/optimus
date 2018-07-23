package com.optimus.netty.server.impl;

import com.optimus.netty.handler.TimeHandler;
import com.optimus.netty.server.NettyServer;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * Created by Administrator on 2018/6/22.
 */
public class DiscardServer implements NettyServer {
    @Override
    public void start(int port) {
        System.out.println(System.getProperty("test.io.threads.hold"));
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();

        try {

            ServerBootstrap server = new ServerBootstrap();
            server.group(bossGroup,workerGroup);
            server.channel(NioServerSocketChannel.class);
            server.childHandler(new ChannelInitializer<SocketChannel>() {
                @Override
                protected void initChannel(SocketChannel ch) throws Exception {
//                    ChannelHandler c = new EachServerHandler();
                    ChannelHandler c = new TimeHandler();
                    ch.pipeline().addLast(c);
                }
            });
            server.option(ChannelOption.SO_BACKLOG,128);
            server.childOption(ChannelOption.SO_KEEPALIVE,true);

            ChannelFuture f = server.bind(port).sync();
            f.channel().closeFuture().sync();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }
}
