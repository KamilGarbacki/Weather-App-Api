package com.weatherApp.service;

import com.weatherApp.apiClient.ApiClient;
import com.weatherApp.exception.InvalidDataException;
import com.weatherApp.model.ReportSummary;
import com.weatherApp.model.WeatherData;
import com.weatherApp.model.WeeklyWeatherReport;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class WeatherService {

    private final ApiClient apiClient;

    private boolean isDataInvalid(double latitude, double longitude){
        double minLatitude = -90.0;
        double maxLatitude = 90.0;
        double minLongitude = -180.0;
        double maxLongitude = 180.0;

        return latitude < minLatitude || latitude > maxLatitude || longitude < minLongitude|| longitude > maxLongitude;
    }

    public WeeklyWeatherReport getWeeklyReport(double latitude, double longitude) {
        if(isDataInvalid(latitude, longitude)){
            throw new InvalidDataException("Latitude and longitude are invalid");
        }

        WeatherData reportData = this.apiClient.getWeatherData(
                latitude,
                longitude,
                null,
                List.of(
                        "weather_code",
                        "temperature_2m_max",
                        "temperature_2m_min",
                        "sunshine_duration"
                ),
                "auto"
        );

        double installationPower = 2.5;
        double efficiency = 0.2;
        double secondsInHour = 3600;

        List<Double> energyGenerated = new ArrayList<>();
        reportData.daily().sunshine_duration().forEach(sunDur -> energyGenerated.add((sunDur / secondsInHour) *efficiency*installationPower));

        return new WeeklyWeatherReport(
                reportData.daily().time(),
                reportData.daily().weather_code(),
                reportData.daily().temperature_2m_max(),
                reportData.daily().temperature_2m_min(),
                energyGenerated
        );
    }

    public ReportSummary getWeeklySummary(double latitude, double longitude) {
        if(isDataInvalid(latitude, longitude)){
            throw new InvalidDataException("Latitude and longitude are invalid");
        }

        WeatherData summaryData = this.apiClient.getWeatherData(
                latitude,
                longitude,
                List.of("surface_pressure"),
                List.of(
                        "sunshine_duration",
                        "temperature_2m_max",
                        "temperature_2m_min",
                        "precipitation_sum"
                ),
                "auto"
        );

        double averagePressure = summaryData.hourly().surface_pressure().stream()
                .mapToDouble(d -> d)
                .average()
                .orElse(0.0);

        double averageSunDur = summaryData.daily().sunshine_duration().stream()
                .mapToDouble(d -> d)
                .average()
                .orElse(0.0);

        double maxTemp = Collections.max(summaryData.daily().temperature_2m_max());
        double minTemp = Collections.min(summaryData.daily().temperature_2m_min());

        int daysWithPrecipitations = (int) summaryData.daily().precipitation_sum().stream()
                .filter(p -> p > 0)
                .count();

        return new ReportSummary(
                averagePressure,
                averageSunDur,
                maxTemp,
                minTemp,
                daysWithPrecipitations >= 4
        );
    }
}
