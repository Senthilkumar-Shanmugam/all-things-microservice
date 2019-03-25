package com.exmaple.common;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class SpringCommonApp {
	public static void main(String[] args) {
		SpringApplication.run(SpringCommonApp.class, args);
	}
}
