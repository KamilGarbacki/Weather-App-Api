package com.weatherApp.controller;

import com.weatherApp.model.ReportSummary;
import com.weatherApp.model.WeeklyWeatherReport;
import com.weatherApp.service.WeatherService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path="/api/v1/weather")
public class WeatherController {

    private final WeatherService weatherService;

    public WeatherController(WeatherService weatherService) {
        this.weatherService = weatherService;
    }

    @GetMapping("/report")
    public WeeklyWeatherReport getWeeklyReport(@RequestParam double latitude,
                                               @RequestParam double longitude) {

        return weatherService.getWeeklyReport(latitude, longitude);
    }

    @GetMapping("/summary")
    public ReportSummary getWeeklySummary(@RequestParam double latitude,
                                          @RequestParam double longitude) {
        return weatherService.getWeeklySummary(latitude, longitude);
    }
}
