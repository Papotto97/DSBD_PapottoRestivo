package com.unict.sagaorchestration;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class SagaOrchestrationApplication {

	public static void main(String[] args) {
		SpringApplication.run(SagaOrchestrationApplication.class, args);
	}

}
