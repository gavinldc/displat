package com.dc.nettyclient;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

/**
 * 从Eureka获取server列表
 * @author gavin
 *
 */
@Component
@RibbonClient(name = "discover-service", configuration = RibbonConfigration.class)
public class RibbonDiscover {

	@Autowired
	private RestTemplate restTemplate;
	
	private String discoverServer(){
		
		restTemplate.getForObject("http://discover-service/getEnv", String.class);
		
		return null;
		
	}
	
}
