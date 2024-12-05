package com.link.cart_service.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.client.RestTemplate;

public class RestCall {

	private static final Logger LOGGER = LoggerFactory.getLogger(RestCall.class);

	private RestTemplate restTemplate;

	public RestCall(RestTemplate restTemplate) {
		this.restTemplate = restTemplate;
	}

	public <T> T getForEntity(String url, Class<T> responseType) {
		LOGGER.info("Retrieve entity by: {}", url);
		return restTemplate.getForEntity(url, responseType).getBody();
	}
}
