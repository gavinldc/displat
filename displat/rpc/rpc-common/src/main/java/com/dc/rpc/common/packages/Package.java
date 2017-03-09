package com.dc.rpc.common.packages;


import java.nio.ByteBuffer;

import com.gc.common.BitConverter;


/**
 * 协议包，messageId 和 protoBuf对象
 * @author gavin
 *
 */
public class Package {

	private int messageId;
	
	private com.google.protobuf.GeneratedMessage proto;
	
	private byte[] body;
	
	public Package(int messageId,com.google.protobuf.GeneratedMessage proto){
		this.messageId=messageId;
		this.proto=proto;
	}
	
	public Package(int messageId,byte[] bytes){
		this.messageId=messageId;
		body=bytes;
	}
	
    public byte[] toByteArray(){
    	byte[] protoBytes=new byte[0];
    	if(proto!=null){
    		protoBytes=proto.toByteArray();
    	}
    	int len=4+protoBytes.length;
    	ByteBuffer buffer=ByteBuffer.allocate(len);
    	buffer.put(BitConverter.getBytes(messageId));
    	buffer.put(protoBytes);
    	return buffer.array();
    }
    
    public byte[] getBody() {
		return body;
	}

	public void setBody(byte[] body) {
		this.body = body;
	}

	public int getMessageId() {
		return messageId;
	}

	public void setMessageId(int messageId) {
		this.messageId = messageId;
	}
    
}
