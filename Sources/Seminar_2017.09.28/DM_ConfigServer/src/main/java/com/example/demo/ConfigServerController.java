package com.example.demo;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ConfigServerController {

	@Value("${spring.cloud.config.server.git.uri}") String uri;
	
	@RequestMapping("/test")
	public String getTest(){
		return "TEST : " + this.uri;
	}
	
}
