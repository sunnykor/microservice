package com.example.demo;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GreetingController {
	
	@Autowired private GreetingService service;
	@Value("${info.description}") private String desc;
	@Value("${info.url}") private String url;
	
	//## It's for the our Load-Balancer.
	//## It's also being used by Ribbon as a url of PING.
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String checkPing(){		
		return "OK!";
	}	
	
	//## It's to check the userService that it's own service.
	@RequestMapping(value = {"/status", "/status/{name}"}, method = RequestMethod.GET)
	public String checkStatus(@PathVariable Map<String, String> nameMap){
		String name = nameMap.containsKey("name") ? nameMap.get("name") + ", " : "" ;
		return name + desc + " ("+this.url+")";
	}
	
	//#############################################
	//##### From here #####
	//#############################################
	
	@RequestMapping(value = "/hello", method = RequestMethod.GET)
	public String goHello(){
		return this.service.getHello();
	}
	
	//## TEST #1
	@RequestMapping(value = "/**", method = RequestMethod.GET)
	public String goPattern1(HttpServletRequest request){
		return this.url + this.service.getPattern1(request);
	}

}
