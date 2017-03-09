package com.dc.nettyclient;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;

import com.netflix.client.config.IClientConfig;
import com.netflix.loadbalancer.IPing;
import com.netflix.loadbalancer.IRule;
import com.netflix.loadbalancer.PingUrl;
import com.netflix.loadbalancer.RandomRule;

public class RibbonConfigration {
	 @Autowired  
	    private IClientConfig ribbonClientConfig;  
	   
	    @Bean  
	    public IPing ribbonPing(IClientConfig config) {  
	        // ping url will try to access http://microservice-provider/provider/ to  
	        // see if reponse code is 200 . check PingUrl.isAlive()  
	        // param /provider/ is the context-path of provider service  
	        return new PingUrl(false, "/provider/");  
	    }  
	  
	 
	    @Bean  
	    public IRule ribbonRule(IClientConfig config) {  
	        // return new AvailabilityFilteringRule();  
	         return new RandomRule();//  
	        // return new BestAvailableRule();  
	        // return new RoundRobinRule();//��ѯ  
	        // return new WeightedResponseTimeRule();  
	        // return new RetryRule();  
	        // return new ZoneAvoidanceRule();  
	    }  
}
