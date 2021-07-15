package com.unict.auctionmanager;

import java.io.IOException;
import java.net.InetAddress;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.util.StreamUtils;

@Configuration
@PropertySource("classpath:application.properties")
@Component

public class RestTemplateRequestInterceptor implements ClientHttpRequestInterceptor {

	private static final Logger LOGGER = LoggerFactory.getLogger(RestTemplateRequestInterceptor.class);

	private static String appName;

	@Value("${spring.application.name}")
	public void setAppName(String value) {
		RestTemplateRequestInterceptor.appName = value;
	}

	@Override
	public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution)
			throws IOException {

		request.getHeaders().add("RequestApplicationName", appName);
		request.getHeaders().add("CallingIP", InetAddress.getLocalHost().getHostAddress());
		LOGGER.info("============================ Request =======================================");
		LOGGER.info("Request Sent:");
		LOGGER.info("----------------------------------------------------------------------------");
		LOGGER.info("URI         : {}", request.getURI());
		LOGGER.info("Headers     : {}", request.getHeaders());
		LOGGER.info("----------------------------------------------------------------------------");
		LOGGER.info("Body:");
		LOGGER.info("Request body: {}", new String(body, StandardCharsets.UTF_8));

		ClientHttpResponse response = execution.execute(request, body);
		logResponse(response);
		return response;
	}

	private void logResponse(ClientHttpResponse response) throws IOException {
		LOGGER.info("============================ Response begin =======================================");
		LOGGER.info("Status code  : {}", response.getStatusCode());
		LOGGER.info("Status text  : {}", response.getStatusText());
		LOGGER.info("Headers      : {}", response.getHeaders());
		LOGGER.info("Response body: {}", StreamUtils.copyToString(response.getBody(), Charset.defaultCharset()));
		LOGGER.info("============================ Response end =========================================");
	}

}