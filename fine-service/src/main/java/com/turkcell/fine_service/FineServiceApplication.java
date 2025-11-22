package com.turkcell.fine_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class FineServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(FineServiceApplication.class, args);
	}

}
