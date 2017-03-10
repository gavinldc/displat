package com.dc.nettyclient;


import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.dc.rpc.common.codec.CustomProtobufDecoder;
import com.dc.rpc.common.codec.CustomProtobufEncoder;
import com.gc.common.Convert;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.LengthFieldPrepender;

@Component
public class Client {
	private Logger log = Logger.getLogger(this.getClass());
	
	@Autowired
	private RibbonDiscover ribbonDiscover;

	public ChannelFuture connect() throws Exception {
		// 配置客户端NIO线程组
		EventLoopGroup group = new NioEventLoopGroup();

		Bootstrap b = new Bootstrap();
		return connect(b, group);
	}

	public ChannelFuture connect(Bootstrap b, EventLoopGroup group) {
		try {
			b.group(group).channel(NioSocketChannel.class).option(ChannelOption.TCP_NODELAY, true)
					.handler(new ChannelInitializer<SocketChannel>() {
						@Override
						public void initChannel(SocketChannel ch) throws Exception {
							ch.pipeline().addLast(new LengthFieldBasedFrameDecoder(1024, 0, 4, 0, 4));
							ch.pipeline().addLast(new CustomProtobufDecoder());
							ch.pipeline().addLast(new LengthFieldPrepender(4));
							ch.pipeline().addLast(new CustomProtobufEncoder());
							ch.pipeline().addLast(new ProtoClientHandler(Client.this));
						}
					});

			//获取服务器信息
			String[] serverInfo=ribbonDiscover.getServerInfo();
			
			// 发起异步连接操作
			ChannelFuture f = b.connect(serverInfo[0],Convert.parseInt(serverInfo[1])).addListener(new ConnectListener(this)).sync();
			if (f.isSuccess()) {
				log.info("连接成功======port:" + serverInfo[1] + "===host:" + serverInfo[0]);
			}
			// 当代客户端链路关闭
			// f.channel().closeFuture().sync();
			return f;
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			// 优雅退出，释放NIO线程组
			// group.shutdownGracefully();
		}
		return null;
	}
}
