package com.unict.sagaorchestration.config;

import org.apache.camel.CamelContext;
import org.apache.camel.saga.InMemorySagaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CamelConfig {

	@Autowired
	CamelContext camelContext;

	@Bean
	public void addContext() throws Exception {
		InMemorySagaService service = new InMemorySagaService();
		camelContext.addService(service);
	}
}