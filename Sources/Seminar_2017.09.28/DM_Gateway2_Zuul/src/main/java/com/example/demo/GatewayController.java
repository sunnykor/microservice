package com.example.demo;

import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RefreshScope
@RestController
public class GatewayController {
	
	@Value("${info.description}") private String desc;
	@Value("${info.url}") private String url;
	
	//## It's for the defaultGateway.
	//## It's also being used by Ribbon as a url of PING.
	@RequestMapping(value = "/ping", method = RequestMethod.GET)
	public String checkPing(){		
		return "OK!";
	}
	
	@RequestMapping(value = "/gw02Status", method = RequestMethod.GET)
	public String checkStatus(@PathVariable Map<String, String> nameMap){
		String name = nameMap.containsKey("name") ? nameMap.get("name") + ", " : "" ;
		return name + desc + " ("+this.url+")";
	}
	
	@RequestMapping(value = "/gw02Status/**", method = RequestMethod.GET)
	public String saySomething(){		
		return "("+this.url+") It's not ready yet!";
	}

}
