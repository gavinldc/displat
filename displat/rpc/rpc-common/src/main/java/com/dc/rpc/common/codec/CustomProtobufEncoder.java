package com.dc.rpc.common.codec;


import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

public class CustomProtobufEncoder extends MessageToByteEncoder<com.dc.rpc.common.packages.Package>{

	@Override
	protected void encode(ChannelHandlerContext ctx, com.dc.rpc.common.packages.Package pack, ByteBuf out) throws Exception {
		out.writeBytes(pack.toByteArray());
	}

}
