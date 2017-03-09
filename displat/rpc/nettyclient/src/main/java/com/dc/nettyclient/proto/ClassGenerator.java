package com.dc.nettyclient.proto;


import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.Scanner;

public class ClassGenerator {
	public static void mains(String[] args) {
		String strCmd = "D:/protobuf/protobuf-master/protoc.exe -I=D:/eclipse-nettyclient/rpc/nettyclient/src/main/java/com/gc/nettyclient/proto --java_out=D:/eclipse-nettyclient/rpc/nettyclient/src/main/java/com/gc/nettyclient/pojo D:/eclipse-nettyclient/rpc/nettyclient/src/main/java/com/gc/nettyclient/proto/conf.proto";
		try {
			InputStream is = Runtime.getRuntime().exec(strCmd).getInputStream();
			byte[] buffer = new byte[1024];
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			while (is.read(buffer) > -1) {
				baos.write(buffer);
			}
			System.out.println(new String(baos.toByteArray()));
		} catch (Throwable e) {
			e.printStackTrace();
		}
	}

	@SuppressWarnings("resource")
	private void writeHandler() throws IOException {
		// 扫描配置
		String path = System.getProperty("user.dir");
		String procolPath = path + "/src/main/java/com/gc/nettyclient/proto/";
		String messageIdPath = path + "/src/main/java/com/gc/nettyclient/pojo/";
		String handlerPath = path + "/src/main/java/com/gc/nettyclient/pojo/";
		// 打开messageId
		File messageIdFile = new File(messageIdPath + "MessageId.java");
		FileChannel messageIdFC = new FileOutputStream(messageIdFile).getChannel();
		messageIdFC.write(ByteBuffer.wrap("package com.gc.nettyclient.pojo;".getBytes()));
		messageIdFC.write(ByteBuffer.wrap("\n".getBytes()));
		messageIdFC.write(ByteBuffer.wrap("public class MessageId {".getBytes()));
		messageIdFC.write(ByteBuffer.wrap("\n".getBytes()));
		messageIdFC.write(ByteBuffer.wrap("public static final int HEATBEAT= 0;//默认心跳".getBytes("utf-8")));
		messageIdFC.write(ByteBuffer.wrap("\n".getBytes()));
		// 打开SingleHandler
		File singleHandlerFile = new File(handlerPath + "SingleHandler.java");
		FileChannel singleHandlerFC = new FileOutputStream(singleHandlerFile).getChannel();
		singleHandlerFC.write(ByteBuffer.wrap("package com.gc.nettyclient.pojo;".getBytes()));
		singleHandlerFC.write(ByteBuffer.wrap("\n".getBytes()));
		singleHandlerFC.write(ByteBuffer.wrap("import com.gc.rpc.connection.Connection;".getBytes()));
		singleHandlerFC.write(ByteBuffer.wrap("\n".getBytes()));
		singleHandlerFC.write(ByteBuffer.wrap("import com.gc.rpc.connection.ConnectionManager;".getBytes()));
		singleHandlerFC.write(ByteBuffer.wrap("\n".getBytes()));
		singleHandlerFC.write(ByteBuffer.wrap("import com.gc.rpc.connection.ConnectionState;".getBytes()));
		singleHandlerFC.write(ByteBuffer.wrap("\n".getBytes()));
		singleHandlerFC.write(ByteBuffer.wrap("import com.google.protobuf.InvalidProtocolBufferException;".getBytes()));
		singleHandlerFC.write(ByteBuffer.wrap("\n".getBytes()));
		singleHandlerFC.write(ByteBuffer.wrap("import org.apache.log4j.Logger;\n".getBytes()));
		singleHandlerFC.write(ByteBuffer.wrap("import io.netty.util.HashedWheelTimer;\n".getBytes()));
		singleHandlerFC.write(ByteBuffer.wrap("import io.netty.util.Timeout;\n".getBytes()));
		singleHandlerFC.write(ByteBuffer.wrap("import io.netty.util.Timer;\n".getBytes()));
		singleHandlerFC.write(ByteBuffer.wrap("import io.netty.util.TimerTask;\n".getBytes()));
		singleHandlerFC.write(ByteBuffer.wrap("import java.util.concurrent.TimeUnit;\n".getBytes()));
		singleHandlerFC.write(ByteBuffer.wrap("public abstract class SingleHandler {".getBytes()));
		singleHandlerFC.write(ByteBuffer.wrap("\n".getBytes()));
		singleHandlerFC.write(ByteBuffer.wrap("private Logger log=Logger.getLogger(this.getClass());\n".getBytes()));
		singleHandlerFC.write(ByteBuffer.wrap("private static final Timer HASHED_WHEEL_TIMER = new HashedWheelTimer();\n".getBytes()));
		
		Scanner scaner = new Scanner(new File(procolPath + "procol.proc"));
		StringBuffer sb = new StringBuffer();// handler process函数
		sb.append("public void process(com.gc.rpc.packages.Package pack,Connection connection){\n");
		sb.append("switch (pack.getMessageId()) {\n");
		sb.append("case MessageId.HEATBEAT:\n");
		sb.append("log.info(\"receive heartbeat\");\n");
		sb.append("try {\n");
		sb.append("startHeartBeat(5);\n");
		sb.append("} catch (Exception e1) {\n");
		sb.append("e1.printStackTrace();\n");
		sb.append("}\n");
		sb.append("return;\n");
		while (scaner.hasNextLine()) {
			String str = scaner.nextLine();
			String[] strs = str.split(":");
			// 写messageid
			messageIdFC.write(ByteBuffer
					.wrap(("public static final int SERVER_TO_CLIENT_" + strs[0].toUpperCase() + "=" + strs[1] + ";")
							.getBytes("utf-8")));
			messageIdFC.write(ByteBuffer.wrap("\n".getBytes()));
			messageIdFC.write(ByteBuffer
					.wrap(("public static final int CLIENT_TO_SERVER_" + strs[0].toUpperCase() + "=" + strs[3] + ";")
							.getBytes("utf-8")));
			messageIdFC.write(ByteBuffer.wrap("\n".getBytes()));
			// 写handler
			singleHandlerFC.write(ByteBuffer.wrap(("public abstract void " + strs[0] + "(Connection connection, Message"
					+ strs[2] + "." + strs[2] + " proto);").getBytes()));
			singleHandlerFC.write(ByteBuffer.wrap("\n".getBytes()));
			// proccess 函数
			sb.append("case MessageId.SERVER_TO_CLIENT_" + strs[0].toUpperCase() + ":\n");
			sb.append("Message" + strs[2] + "." + strs[2] + " proto" + strs[3] + ";\n");
			sb.append("try {\n");
			sb.append("proto" + strs[3] + " = Message" + strs[2] + "." + strs[2] + ".parseFrom(pack.getBody());\n");
			sb.append(strs[0] + "(connection,proto" + strs[3] + ");\n");
			sb.append("} catch (InvalidProtocolBufferException e) {\n");
			sb.append("e.printStackTrace();\n");
			sb.append("}\n");
			sb.append("return;\n");
		}
		messageIdFC.write(ByteBuffer.wrap("}".getBytes()));
		messageIdFC.close();
		sb.append("default:\n");
		sb.append("break;\n");
		sb.append("}\n");
		sb.append("}\n");
		
		singleHandlerFC.write(ByteBuffer.wrap(sb.toString().getBytes()));
		singleHandlerFC.write(ByteBuffer.wrap("private void startHeartBeat(final int heartbeat) throws Exception {\n".getBytes()));
		singleHandlerFC.write(ByteBuffer.wrap("HASHED_WHEEL_TIMER.newTimeout(new TimerTask() {\n".getBytes()));
		singleHandlerFC.write(ByteBuffer.wrap("public void run(Timeout timeout) throws Exception {\n".getBytes()));
		singleHandlerFC.write(ByteBuffer.wrap("if(healthCheck()){\n".getBytes()));
		singleHandlerFC.write(ByteBuffer.wrap("com.gc.rpc.packages.Package heartbeatpack=new com.gc.rpc.packages.Package(MessageId.HEATBEAT, new byte[0]);\n".getBytes()));
		singleHandlerFC.write(ByteBuffer.wrap("ConnectionManager.getInstance().getConnection().send(heartbeatpack, null);\n".getBytes()));
		singleHandlerFC.write(ByteBuffer.wrap("}\n".getBytes()));
		singleHandlerFC.write(ByteBuffer.wrap("}\n".getBytes()));
		singleHandlerFC.write(ByteBuffer.wrap("}, heartbeat, TimeUnit.SECONDS);\n".getBytes()));
		singleHandlerFC.write(ByteBuffer.wrap("}\n".getBytes()));
		singleHandlerFC.write(ByteBuffer.wrap("private boolean healthCheck(){\n".getBytes()));
		singleHandlerFC.write(ByteBuffer.wrap("if(ConnectionManager.getInstance().getConnection().getState().equals(ConnectionState.Login)){\n".getBytes()));
		singleHandlerFC.write(ByteBuffer.wrap("return true;\n".getBytes()));
		singleHandlerFC.write(ByteBuffer.wrap("}\n".getBytes()));
		singleHandlerFC.write(ByteBuffer.wrap("return false;\n".getBytes()));
		singleHandlerFC.write(ByteBuffer.wrap("}\n".getBytes()));
		singleHandlerFC.write(ByteBuffer.wrap("}\n".getBytes()));
		sb.append("}");
		singleHandlerFC.close();

	}

	public static void main(String[] msg) throws IOException {
		new ClassGenerator().writeHandler();
	}

}
