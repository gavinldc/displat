package com.dc.nettyserver;




import java.util.concurrent.TimeUnit;

import com.dc.rpc.common.codec.CustomProtobufDecoder;
import com.dc.rpc.common.codec.CustomProtobufEncoder;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.LengthFieldPrepender;
import io.netty.handler.timeout.IdleStateHandler;

public class Server {

	public ChannelFuture start(int port) throws Exception {
		EventLoopGroup bossGroup = new NioEventLoopGroup();
		EventLoopGroup workerGroup = new NioEventLoopGroup(5);
		try {
			ServerBootstrap b = new ServerBootstrap();
			b.group(bossGroup, workerGroup);
			b.channel(NioServerSocketChannel.class);
			b.childHandler(new ChannelInitializer<SocketChannel>() {
				@Override
				protected void initChannel(SocketChannel ch) throws Exception {
					//decoded
					ch.pipeline().addLast(new LengthFieldBasedFrameDecoder(1024, 0, 4, 0, 4));
					ch.pipeline().addLast(new CustomProtobufDecoder());
					//encoded
					ch.pipeline().addLast(new LengthFieldPrepender(4));
					ch.pipeline().addLast(new CustomProtobufEncoder());
					// 注册handler
					ch.pipeline().addLast(new ProtoServerHandler());
					// 心跳检测
					ch.pipeline().addLast("ping",new IdleStateHandler(60, 45, 30,TimeUnit.SECONDS));
				}
			});
			b.option(ChannelOption.SO_BACKLOG, 128);
			b.childOption(ChannelOption.SO_KEEPALIVE, true);
			//绑定端口 同步等待成功
			ChannelFuture f = b.bind(port).sync();
			//等待服务端监听端口关闭
			f.channel().closeFuture().sync();
			return f;
		} finally {
			workerGroup.shutdownGracefully();
			bossGroup.shutdownGracefully();
		}
	}
}
