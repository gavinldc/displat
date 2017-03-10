package com.dc.gateaway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;

@EnableHystrix  
@EnableHystrixDashboard
@EnableZuulProxy  
@SpringBootApplication
public class ZuulApp {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		SpringApplication.run(ZuulApp.class, args);
	}

}
