package com.everis.latam.BKDProveedoresFlama;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class BkdProveedoresFlamaApplication {

	public static void main(String[] args) {
		SpringApplication.run(BkdProveedoresFlamaApplication.class, args);
	}
	@Bean
	public RestTemplate getRestTemple() {
		return new RestTemplate();
	}

}
