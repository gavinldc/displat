/**  

 * Copyright © 2018pact. All rights reserved.

 *

 * @Title: WSController.java

 * @Prject: websocket

 * @Package: com.dc.displat.ws

 * @Description: TODO

 * @author: gavin.lyu  

 * @date: 2018年12月25日 上午11:54:32

 * @version: V1.0  

 */
package com.dc.displat.ws;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.util.HtmlUtils;

import com.dc.displat.ws.message.ClientMessage;
import com.dc.displat.ws.message.ServerMessage;

/**
 * @author gavin.lyu
 *
 */
@Controller
public class WSController {
	
	@Autowired
	private SimpMessagingTemplate simpMessageTemplate;
	
	
	
	@MessageMapping(GlobalConsts.HELLO_MAPPING)
    @SendTo(GlobalConsts.TOPIC)
    public ServerMessage greeting(ClientMessage message) throws Exception {
		
		simpMessageTemplate.convertAndSend(GlobalConsts.TOPIC, "{\"content\":\"主动推送给你\"}");
		
        return new ServerMessage("Hello, " + HtmlUtils.htmlEscape(message.getContent()) + "!");
    }
}
