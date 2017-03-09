package com.dc.nettyserver.controller;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * 提供本机ip端口号的查询服务
 * @author DELL1
 *
 */
@RestController
public class EnvController {

	@Value("${location.ip}")
	private String ip;
	
	@Value("${location.port}")
	private String port;
	
	@RequestMapping(value="/env")
	public String getEnv(){
		
		StringBuffer sb=new StringBuffer();
		sb.append("{\"ip\":\"");
		sb.append(ip);
		sb.append("\",\"port\":\"");
		sb.append(port);
		sb.append("\"}");
		return sb.toString();
	}
	
	
	
	
	
}
