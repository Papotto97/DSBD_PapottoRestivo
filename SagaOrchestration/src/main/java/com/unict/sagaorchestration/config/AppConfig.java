/**
 * 
 */
package com.unict.sagaorchestration.config;

import org.apache.camel.component.servlet.CamelHttpTransportServlet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

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
	
}
