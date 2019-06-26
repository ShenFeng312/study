package com.sf.eurekatestserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@EnableEurekaServer
@SpringBootApplication
public class EurekatestserverApplication {
	public static void main(String[] args) {
		SpringApplication.run(EurekatestserverApplication.class, args);
	}
}
