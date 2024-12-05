package com.link.cart_service.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import com.link.cart_service.error.RestErrorHandler;
import com.link.cart_service.rest.RestCall;

@Configuration
public class RestTemplateConfig {

	@Bean
	RestTemplate restTemplate() {
		RestTemplate restTemplate = new RestTemplate();
		restTemplate.setErrorHandler(new RestErrorHandler());
		return restTemplate;
	}

	@Bean
	RestCall restCall() {
		return new RestCall(restTemplate());
	}
}
