package com.example.demo;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class GreetingService {
	
	@Value("${info.url}") private String url;
	private StringBuilder sb = new StringBuilder();

	public String getHello(){
		List<String> greetings = Arrays.asList("Hi there", "Greetings", "Salutations");
		Random random = new Random();
		int num = random.nextInt(greetings.size());
		
		this.sb.setLength(0);
		this.sb.append(greetings.get(num)).append(" - ");
		this.sb.append("<font color='red'>").append(this.url).append("</font>");
		
		return this.sb.toString();
	}
	
	public String getPattern1(HttpServletRequest request){
		this.sb.setLength(0);
		this.sb.append("---> ").append(request.getRequestURI()).append("<br />");
		this.sb.append("An API that you requested doesn't exist.");
		
		return this.sb.toString();
	}
	
}
