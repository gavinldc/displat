package com.gc.common;

import java.util.List;


public class ByteUtils {
	
	public static byte[] copy(byte[] src, int off, int len) {
		byte[] receive = new byte[len];
		System.arraycopy(src, off, receive, 0, len);
		return receive;
	}
	
	public static byte[] merge(byte before, byte[] after) {
		byte[] buffer = new byte[after.length + 1];
		buffer[0] = before;
		System.arraycopy(after, 0, buffer, 1, after.length);
		return buffer;
	}
	
	public static byte[] merge(byte[] before,List<Byte> after) {
		byte[] buffer = new byte[before.length + after.size()];
		for (int i = 0;i < before.length;++i) {
			buffer[i]  = before[i];
		}
		for (int i = 0;i < after.size();++i) {
			buffer[i + before.length] = after.get(i);
		}
 		return buffer;
	}
	
	public static byte[] merge(byte[] before, byte[] after) {
		byte[] buffer = new byte[before.length + after.length];
		System.arraycopy(before, 0, buffer, 0, before.length);
		System.arraycopy(after, 0, buffer, before.length, after.length);
 		return buffer;
	}
	
	
}
