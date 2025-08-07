package org.kgarbacki.weatherAppApi.apiClient;

import org.kgarbacki.weatherAppApi.apiClient.response.WeatherForecast;
import org.kgarbacki.weatherAppApi.controller.v1.request.OpenMeteoReq;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.cloud.openfeign.SpringQueryMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(
        name = "openMeteoClient",
        url = "https://api.open-meteo.com/v1"
)
public interface ApiClient {

    @GetMapping("/forecast")
    WeatherForecast getWeatherData(@SpringQueryMap OpenMeteoReq request);
}
