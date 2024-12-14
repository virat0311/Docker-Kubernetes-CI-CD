package com.pandey.microservices.currency_conversion_microservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class CurrencyConversionMicroserviceApplication {

	public static void main(String[] args) {
		SpringApplication.run(CurrencyConversionMicroserviceApplication.class, args);
	}

}
