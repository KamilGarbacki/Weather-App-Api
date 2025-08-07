package org.kgarbacki.weatherAppApi.dto;

import java.time.LocalDate;

public record WeatherReportDto(
        LocalDate date,
        int weatherCode,
        float maxTemp,
        float minTemp,
        float generatedEnergy
) {
}
