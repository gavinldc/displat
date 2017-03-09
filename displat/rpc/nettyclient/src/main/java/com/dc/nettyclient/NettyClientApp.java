package com.dc.nettyclient;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;


@SpringBootApplication
@EnableEurekaClient
@EnableDiscoveryClient
@EnableAutoConfiguration
public class NettyClientApp {

	
	public static void main(String[] msg){
		 new SpringApplicationBuilder(NettyClientApp.class).web(true).run(msg);
	}
	
	@Bean
	@LoadBalanced
	public RestTemplate restTemplate() {
	return new RestTemplate();
	}
	
	
	
}
