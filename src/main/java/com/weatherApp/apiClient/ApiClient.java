package com.weatherApp.apiClient;

import com.weatherApp.model.WeatherData;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(name = "openMeteoClient", url = "https://api.open-meteo.com/v1")
public interface ApiClient {

    @GetMapping("/forecast")
    WeatherData getWeatherData(@RequestParam("latitude") double latitude,
                               @RequestParam("longitude") double longitude,
                               @RequestParam List<String> hourly,
                               @RequestParam List<String> daily,
                               @RequestParam String timezone
    );
}