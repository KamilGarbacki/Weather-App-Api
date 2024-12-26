package com.weatherApp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class WeatherApplicationApi {

	public static void main(String[] args) {
		SpringApplication.run(WeatherApplicationApi.class, args);
	}

}
