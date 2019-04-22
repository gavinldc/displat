/**  

 * Copyright © 2019pact. All rights reserved.

 *

 * @Title: IPController.java

 * @Prject: wsgateway

 * @Package: com.dc.display.wsgateway

 * @Description: TODO

 * @author: gavin.lyu  

 * @date: 2019年4月22日 下午2:43:37

 * @version: V1.0  

 */
package com.dc.display.wsgateway;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import reactor.core.publisher.Mono;

/**
 * @author gavin.lyu
 *
 */
@RestController
public class IPController {
	
	@Autowired
	private WebsocketIpManager websocketManager;
	
	@GetMapping("/ip/get")
	public Mono<String> getWebSocketIp(){
		return Mono.just(websocketManager.getHostByRandom());
	}
}
