package com.dc.servicediscover;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
@EnableAutoConfiguration
public class ServiceDiscoverApplication {
	public static void main(String[] msg){
		 new SpringApplicationBuilder(ServiceDiscoverApplication.class).web(true).run(msg);
	}
}
