package com.schemax.foodforward;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class })
public class FoodforwardApplication {

	public static void main(String[] args) {
		SpringApplication.run(FoodforwardApplication.class, args);
	}

}
