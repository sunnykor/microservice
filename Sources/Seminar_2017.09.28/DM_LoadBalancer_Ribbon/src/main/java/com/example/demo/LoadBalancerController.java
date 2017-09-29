package com.example.demo;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RefreshScope
@RestController
public class LoadBalancerController {
	
	@Autowired private LoadBalancerService service;
	@Value("${info.description}") private String desc;
	@Value("${info.url}") private String url;
	
	//## It's to check the Load-Balancer that it's own service. 
	@RequestMapping(value = "/elbStatus", method = RequestMethod.GET)
	public String getHello(){
		return desc + " ("+this.url+")";
	}
	
	@RequestMapping(value = {"/**", "/*/**"}, method = RequestMethod.GET)
	public String goPast(HttpServletRequest request){
		return this.service.goUserService(request);
	}

}
