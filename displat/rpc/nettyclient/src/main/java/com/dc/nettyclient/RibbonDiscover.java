package com.dc.nettyclient;


import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.gc.common.Strings;

/**
 * 从Eureka获取server列表
 * @author gavin
 *
 */
@Component
public class RibbonDiscover {

	private Logger logger=Logger.getLogger(this.getClass());
	
	 @Autowired
	 RestTemplate restTemplate;
	 
	 public String[] getServerInfo()throws Exception{
		 String res= restTemplate.getForEntity("http://server-registry-1/env", String.class).getBody();
		 if(Strings.isNullOrEmpty(res)){
			 throw new Exception("active connection not found");
		 }
		 logger.info("get serverinfo :"+res);
		 return res.split(",");
	 }
	 
	
}
