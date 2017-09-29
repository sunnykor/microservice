package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;

import com.netflix.client.config.IClientConfig;
import com.netflix.loadbalancer.AvailabilityFilteringRule;
import com.netflix.loadbalancer.IPing;
import com.netflix.loadbalancer.IRule;
import com.netflix.loadbalancer.PingUrl;

public class LoadBalancerConfig {

	@Autowired
	IClientConfig config;
	
	@Bean
	public IPing iping(IClientConfig config) {
		PingUrl iping = new PingUrl();
		iping.setPingAppendString("/ping");
		return iping;
//		return new PingUrl();
	}
	
	@Bean
	public IRule irule(IClientConfig config){
		return new AvailabilityFilteringRule();
		//return new WeightedResponseTimeRule();
	}
	
}
