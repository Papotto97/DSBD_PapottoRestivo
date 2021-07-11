package com.unict.walletmanager;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class WalletManagerApplication {

	public static void main(String[] args) {
		SpringApplication.run(WalletManagerApplication.class, args);
	}

}
