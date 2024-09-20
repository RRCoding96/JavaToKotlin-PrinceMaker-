package com.makers.princemaker;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class PrinceMakerApplication {

	public static void main(String[] args) {
		SpringApplication.run(PrinceMakerApplication.class, args);
	}

}
