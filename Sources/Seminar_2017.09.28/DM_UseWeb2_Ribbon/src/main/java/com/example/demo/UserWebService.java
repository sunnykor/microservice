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
public class UserWebService {
	
	@Autowired private RestTemplate restGreeting;
	@Value("${info.routingUrl}") public String host;
	@Value("${info.description}") public String desc;
	private RestTemplate restCalendar = new RestTemplate();
	
	//@HystrixCommand(fallbackMethod = "failureGetService")
	public String getGw01Status(HttpServletRequest request){
		String url = this.host + request.getRequestURI();
		String rtnValue = this.restGreeting.getForObject(URI.create(url), String.class);
		
		return rtnValue;
	}
	
	public String getGw02Status(HttpServletRequest request){
		String url = "http://localhost:10003" + request.getRequestURI();
		String rtnValue = this.restCalendar.getForObject(URI.create(url), String.class);
		
		return rtnValue;
	}
	
	public String getDescription(){
		return this.desc;
	}
	
	@HystrixCommand(fallbackMethod = "failureGetService", commandKey = "greeting")
	public String getGreeting(HttpServletRequest request, String svcNm){
		String service = (svcNm != null && !svcNm.trim().equals("")) ? "/"+svcNm : "";
		String url = this.host + request.getRequestURI().replace("/portal", "/greetingSvc") + service;
		String rtnValue = this.restGreeting.getForObject(URI.create(url), String.class);
		
		return rtnValue;
	}
	
	@HystrixCommand(fallbackMethod = "failureGetService", commandKey = "calendar")
	public String getCalendar(HttpServletRequest request, String svcNm){
		String service = (svcNm != null && !svcNm.trim().equals("")) ? "/"+svcNm : "";
		String url = "http://localhost:10003" + request.getRequestURI().replace("/portal", "/calendarSvc") + service;
		String rtnValue = restCalendar.getForObject(URI.create(url), String.class);
		
		return rtnValue;
	}
	
	public String failureGetService(HttpServletRequest request, String svcNm){
		return "("+svcNm+") your service or request didn't work out!";
	}

}