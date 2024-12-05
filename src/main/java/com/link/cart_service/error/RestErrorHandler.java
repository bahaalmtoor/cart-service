package com.link.cart_service.error;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.http.HttpStatus;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.client.DefaultResponseErrorHandler;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.ResponseErrorHandler;

@Component
public class RestErrorHandler implements ResponseErrorHandler {

	private static String convertInputStreamToString(InputStream in) throws IOException {
		Stream<String> lines = new BufferedReader(new InputStreamReader(in)).lines();
		return lines.collect(Collectors.joining(System.getProperty("line.separator")));
	}

	@Override
	public boolean hasError(ClientHttpResponse response) throws IOException {
		return new DefaultResponseErrorHandler().hasError(response);
	}

	@Override
	public void handleError(ClientHttpResponse response) throws IOException {
		if (response.getStatusCode().is5xxServerError()) {
			throw new HttpClientErrorException(response.getStatusCode());
		} else if (response.getStatusCode().is4xxClientError()) {
			if (response.getStatusCode() == HttpStatus.NOT_FOUND) {
				throw new NotFoundException(convertInputStreamToString(response.getBody()));
			}
		}
	}
}
