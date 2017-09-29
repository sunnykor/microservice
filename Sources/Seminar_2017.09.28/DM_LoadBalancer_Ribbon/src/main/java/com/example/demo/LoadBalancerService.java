package com.example.demo;

import java.net.URI;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

@RefreshScope
@Service
public class LoadBalancerService {

	@Autowired private RestTemplate restTemplate;
	@Value("${info.routingUrl}") private String host;
	
	@HystrixCommand(fallbackMethod = "failureGoPast")
	public String goUserService(HttpServletRequest request){
		String url = this.host + request.getRequestURI();
		String rtnValue = this.restTemplate.getForObject(URI.create(url), String.class);
		
		return rtnValue;
	}
	
	@HystrixCommand(fallbackMethod = "goUserService")
	public String failureGoPast(HttpServletRequest request){
		String url = this.host + request.getRequestURI();
		String rtnValue = this.restTemplate.getForObject(URI.create(url), String.class);
		
		return rtnValue;
	}
	
}
