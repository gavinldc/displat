/**  

 * Copyright © 2018pact. All rights reserved.

 *

 * @Title: WebSocketConfig.java

 * @Prject: websocket

 * @Package: com.dc.displat.ws

 * @Description: TODO

 * @author: gavin.lyu  

 * @date: 2018年12月25日 上午11:44:57

 * @version: V1.0  

 */
package com.dc.displat.ws;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

/**
 * @author gavin.lyu
 *
 */
@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {
	@Override
	public void configureMessageBroker(MessageBrokerRegistry config) {
		ThreadPoolTaskScheduler te = new ThreadPoolTaskScheduler();
        te.setPoolSize(1);
        te.setThreadNamePrefix("wss-heartbeat-thread-");
        te.initialize();
		config.enableSimpleBroker("/topic").setHeartbeatValue(new long[]{500,500}).setTaskScheduler(te);
		config.setApplicationDestinationPrefixes(GlobalConsts.APP_PREFIX);
	}

	@Override
	public void registerStompEndpoints(StompEndpointRegistry registry) {
		registry.addEndpoint(GlobalConsts.ENDPOINT).setAllowedOrigins("*").withSockJS();
	}
}
