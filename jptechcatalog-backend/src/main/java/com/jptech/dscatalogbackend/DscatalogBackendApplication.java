package com.jptech.dscatalogbackend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication()
public class DscatalogBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(DscatalogBackendApplication.class, args);
	//	System.out.println(new BCryptPasswordEncoder().encode("ADM@20jpm"));
	}

}
