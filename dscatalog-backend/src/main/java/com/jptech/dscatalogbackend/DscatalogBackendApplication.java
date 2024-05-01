package com.jptech.dscatalogbackend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(exclude = SecurityAutoConfiguration.class)
public class DscatalogBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(DscatalogBackendApplication.class, args);
	}

}
