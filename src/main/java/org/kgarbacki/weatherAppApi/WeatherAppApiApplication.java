package org.kgarbacki.weatherAppApi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class WeatherAppApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(WeatherAppApiApplication.class, args);
    }

}
