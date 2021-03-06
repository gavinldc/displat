package com.dc.nettyclient;


import java.util.concurrent.TimeUnit;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.EventLoop;

/**
 * 保证断线重连
 * 
 * @author gavin
 *
 */
public class ConnectListener implements ChannelFutureListener {

	private Client client;

	public ConnectListener(Client client) {
		this.client = client;
	}

	public void operationComplete(ChannelFuture future) throws Exception {
		if (!future.isSuccess()) {

			System.out.println("Reconnect");

			final EventLoop loop = future.channel().eventLoop();

			loop.schedule(new Runnable() {

				public void run() {

					client.connect(new Bootstrap(), loop);

				}

			}, 1L, TimeUnit.SECONDS);

		}

	}

}
