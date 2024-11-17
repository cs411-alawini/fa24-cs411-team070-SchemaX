package com.schemax.foodforward;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories(basePackages = "com.schemax.foodforward.repository")
public class FoodforwardApplication {
	public static void main(String[] args) {
		SpringApplication.run(FoodforwardApplication.class, args);
	}
}
