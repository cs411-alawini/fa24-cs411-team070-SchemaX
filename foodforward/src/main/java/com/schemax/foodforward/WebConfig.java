package com.schemax.foodforward;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

	@Override
	public void addCorsMappings(CorsRegistry registry) {
		registry.addMapping("/**") // Apply to all endpoints
				.allowedOrigins("http://localhost:3000") // Allow requests from React app's URL
				.allowedMethods("GET", "POST", "PUT", "DELETE") // Specify allowed HTTP methods
				.allowedHeaders("*") // Allow any headers
				.allowCredentials(true); // Allow credentials if needed (cookies, authorization headers, etc.)
	}
}