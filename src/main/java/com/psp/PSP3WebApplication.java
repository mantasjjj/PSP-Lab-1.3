package com.psp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication // scan for components in current package and below
public class PSP3WebApplication {

	public static void main(String[] args) {
		SpringApplication.run(PSP3WebApplication.class, args);
	}
}
