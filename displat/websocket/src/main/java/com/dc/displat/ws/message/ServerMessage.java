/**  

 * Copyright © 2018pact. All rights reserved.

 *

 * @Title: ServerMessage.java

 * @Prject: websocket

 * @Package: com.dc.displat.ws.message

 * @Description: TODO

 * @author: gavin.lyu  

 * @date: 2018年12月25日 上午11:53:46

 * @version: V1.0  

 */
package com.dc.displat.ws.message;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author gavin.lyu
 *
 */
@Data
@AllArgsConstructor
public class ServerMessage {
	private String content;
	public ServerMessage() {}
}
