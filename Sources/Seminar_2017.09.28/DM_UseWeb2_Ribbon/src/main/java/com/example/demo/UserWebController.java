package com.example.demo;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RefreshScope
@RestController
public class UserWebController {

	@Autowired private UserWebService service;
	@Value("${info.description}") private String desc;
	@Value("${info.url}") private String url;
	private StringBuffer sb = new StringBuffer();
	
	//## It's for the our Load-Balancer.
	//## It's also being used by Ribbon as a url of PING.
	@RequestMapping(value = "/ping", method = RequestMethod.GET)
	public String checkPing(){		
		return "OK!";
	}	
	
	//## It's to check the userService that it's own service.
	@RequestMapping(value = {"/status", "/status/{name}"}, method = RequestMethod.GET)
	public String checkStatus(@PathVariable Map<String, String> nameMap){
		String name = nameMap.containsKey("name") ? nameMap.get("name") + ", " : "" ;
		return name + desc + " ("+this.url+")";
	}

	//## It's to check the connection to the defaultGateway.
	//## The path, '/gw/', also has to be defined into the defaultGateway's controller. 
	@RequestMapping(value = {"/gw01Status", "/gw01Status/**"}, method = RequestMethod.GET)
	public String checkGateway01(HttpServletRequest request){
		return this.service.getGw01Status(request);
	}
	
	@RequestMapping(value = {"/gw02Status", "/gw02Status/**"}, method = RequestMethod.GET)
	public String checkGateway02(HttpServletRequest request){
		return this.service.getGw02Status(request);
	}
	
	//#############################################
	//##### From here, Calling MicroServices. #####
	//#############################################

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String goDefault(){		
		return "Hello World! ("+this.url+")";
	}
	
	@RequestMapping(value = "/desc", method = RequestMethod.GET)
	public String goDescription(){		
		return this.service.getDescription();
	}
	
	@RequestMapping(value = "/portal", method = RequestMethod.GET)
	public String getPortal(HttpServletRequest request){
		this.sb.setLength(0);
		this.sb.append("인사 서비스: ").append(this.goGreeting(request, "hello"));
		this.sb.append("<br /><br />");
		this.sb.append("달력 서비스: ").append(this.goCalendar(request, "today"));

		return this.sb.toString();
	}
	
	@RequestMapping(value = {"/greetingSvc/**"}, method = RequestMethod.GET)
	public String goGreeting(HttpServletRequest request, String svcNm){
		return this.service.getGreeting(request, svcNm);
	}
	
	@RequestMapping(value = {"/calendarSvc/**"}, method = RequestMethod.GET)
	public String goCalendar(HttpServletRequest request, String svcNm){
		return this.service.getCalendar(request, svcNm);
	}
	
	//## TEST #1
	/*@RequestMapping(value = "/**", method = RequestMethod.GET)
	public String getPattern1(HttpServletRequest request){
		this.sb.setLength(0);
		this.sb.append("---> ").append(request.getRequestURI()).append("<br />");
		this.sb.append("An API that you requested doesn't exist.");
		
		return this.sb.toString();
	}*/
	
}
