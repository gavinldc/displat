/**  

 * Copyright © 2019pact. All rights reserved.

 *

 * @Title: WebsocketIpManager.java

 * @Prject: wsgateway

 * @Package: com.dc.display.wsgateway

 * @Description: websocket 链接管理

 * @author: gavin.lyu  

 * @date: 2019年4月22日 上午11:01:04

 * @version: V1.0  

 */
package com.dc.display.wsgateway;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

/**
 * @author gavin.lyu
 *
 */
@Component
public class WebsocketIpManager {
	
	@Value("${ws.host}")
	private String[] ips;
	
	private String keyFormat = "iplist:%s";
	
	@Autowired
	private RedisTemplate<Object, Object> redis;
	
	
	public void initHost() {
		String key = String.format(keyFormat, "ips");
		Object ipsObj = redis.opsForValue().get(key);
		if(ipsObj!=null) {
			ips=ipsObj.toString().split(",");
		}
	}
	
	/**
	 * 根据当前毫秒时间戳和ip列表的长度取模
	 * @return
	 */
	public String getHostByRandom() {
		Long index = System.currentTimeMillis()%ips.length;
		return ips[index.intValue()];
	}
	
	/**
	 * 根据每个IP的链接数量，返回最小的ip
	 * @return
	 */
	public String getHostByConnections() {
		String key = String.format(keyFormat, "connections");
		Set<Object> set = redis.opsForZSet().range(key, 0, -1);
		return set.iterator().next().toString();
	}
	
}
