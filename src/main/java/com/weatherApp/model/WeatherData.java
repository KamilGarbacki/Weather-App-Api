package com.weatherApp.model;

public record WeatherData(
        DailyData daily,
        HourlyData hourly
) {
}
