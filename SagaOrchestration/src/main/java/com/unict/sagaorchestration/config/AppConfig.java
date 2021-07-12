/**
 * 
 */
package com.unict.sagaorchestration.config;

import java.util.Arrays;
import java.util.Collections;

import org.apache.camel.component.servlet.CamelHttpTransportServlet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.env.Environment;
import org.springframework.http.MediaType;
import org.springframework.http.client.BufferingClientHttpRequestFactory;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.unict.sagaorchestration.RestTemplateRequestInterceptor;

@Configuration
public class AppConfig {
	
	@Autowired
	private Environment env;
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Bean
	ServletRegistrationBean servletRegistrationBean() {
		String contextPath = env.getProperty("camel.servlet.context-path", "");
	    ServletRegistrationBean servlet = new ServletRegistrationBean(new CamelHttpTransportServlet(), contextPath+"/*");
	    servlet.setName("CamelServlet");
	    return servlet;
	}
	
	@Bean
	@Primary
    public RestTemplate restTemplate () {
        RestTemplate restTemplate = new RestTemplate(new BufferingClientHttpRequestFactory(new SimpleClientHttpRequestFactory()));
        MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
        converter.setObjectMapper(new ObjectMapper());
        converter.setSupportedMediaTypes(Arrays.asList(MediaType.APPLICATION_JSON, MediaType.TEXT_PLAIN));
        restTemplate.getMessageConverters().add(converter);
        restTemplate.setInterceptors(Collections.singletonList(new RestTemplateRequestInterceptor()));
        return restTemplate;
    }
	
}
