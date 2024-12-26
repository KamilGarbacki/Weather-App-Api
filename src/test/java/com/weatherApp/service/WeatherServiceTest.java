package com.weatherApp.service;

import com.weatherApp.apiClient.ApiClient;
import com.weatherApp.exception.InvalidDataException;
import com.weatherApp.model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
class WeatherServiceTest {
    private WeatherService underTest;
    @Mock
    private ApiClient apiClient;


    @BeforeEach
    void setUp() {
        underTest = new WeatherService(apiClient);
    }

    @Test
    void getWeeklyReport() {
        List<LocalDate> dates = List.of(
                LocalDate.of(2024, 12, 22),
                LocalDate.of(2024, 12, 23)
        );
        List<Integer> weatherCodes = List.of(80, 85);
        List<Double>  maxTemps = List.of(7.2, 8.4);
        List<Double>  minTemps = List.of(3.5, 2.7);
        List<Double> sunshineDur =  List.of(10.0, 14.2);

        WeatherData apiResponse = new WeatherData(
                new DailyData(
                        dates,
                        weatherCodes,
                        maxTemps,
                        minTemps,
                       sunshineDur,
                        List.of()
                ),
                new HourlyData(
                        List.of()
                )
        );

        double latitude =  52.52;
        double longitude = 13.41;

        when(apiClient.getWeatherData(
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
        )).thenReturn(apiResponse);

        WeeklyWeatherReport result = underTest.getWeeklyReport(latitude, longitude);

        double installationPower = 2.5;
        double efficiency = 0.2;
        double secondsInHour = 3600;

        assertThat(result.getDates()).isEqualTo(dates);
        assertThat(result.getWeatherCodes()).isEqualTo(weatherCodes);
        assertThat(result.getMaxTemps()).isEqualTo(maxTemps);
        assertThat(result.getMinTemps()).isEqualTo(minTemps);
        assertThat(result.getGeneratedEnergies()).isEqualTo(
                List.of(
                        (sunshineDur.get(0) / secondsInHour) * efficiency * installationPower,
                        (sunshineDur.get(1) /secondsInHour) * efficiency * installationPower
                )
        );
    }

    @Test
    void getWeeklyReportThrowsWhenInvalidData() {
        double latitude = -95.0;
        double longitude = 185.0;

        assertThatThrownBy(() -> underTest.getWeeklyReport(latitude, longitude))
                .isInstanceOf(InvalidDataException.class)
                .hasMessage("Latitude and longitude are invalid");
    }

    @Test
    void getWeeklySummary() {
        List<LocalDate> dates = List.of(
                LocalDate.of(2024, 12, 22),
                LocalDate.of(2024, 12, 23)
        );
        List<Integer> weatherCodes = List.of(80, 85);
        List<Double>  maxTemps = List.of(7.2, 8.4);
        List<Double>  minTemps = List.of(3.5, 2.7);
        List<Double> sunshineDur =  List.of(10.0, 14.2);
        List<Double> precipitations = List.of(2.2, 0.0);
        List<Double> pressures = List.of(996.0, 997.0, 998.0, 999.0);

        WeatherData apiResponse = new WeatherData(
                new DailyData(
                        dates,
                        weatherCodes,
                        maxTemps,
                        minTemps,
                        sunshineDur,
                        precipitations
                ),
                new HourlyData(
                        pressures
                )
        );

        double latitude =  52.52;
        double longitude = 13.41;

        when(apiClient.getWeatherData(
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

        )).thenReturn(apiResponse);

        double averagePressure = pressures.stream()
                .mapToDouble(d -> d)
                .average()
                .orElse(0.0);

        double averageSunDur = sunshineDur.stream()
                .mapToDouble(d -> d)
                .average()
                .orElse(0.0);

        double maxTemp = Collections.max(maxTemps);
        double minTemp = Collections.min(minTemps);

        int daysWithPrecipitations = (int) precipitations.stream()
                .filter(p -> p > 0)
                .count();

        ReportSummary actual = underTest.getWeeklySummary(latitude, longitude);

        assertThat(actual.getAverageWeeklyPressure()).isEqualTo(averagePressure);
        assertThat(actual.getAverageSunExposure()).isEqualTo(averageSunDur);
        assertThat(actual.getMaxWeeklyTemp()).isEqualTo(maxTemp);
        assertThat(actual.getMinWeeklyTemp()).isEqualTo(minTemp);
        assertThat(actual.isWeekWithPrecipitation()).isEqualTo(daysWithPrecipitations >= 4);
    }

    @Test
    void getWeeklySummaryThrowsWhenInvalidData() {
        double latitude = -95.0;
        double longitude = 185.0;

        assertThatThrownBy(() -> underTest.getWeeklySummary(latitude, longitude))
                .isInstanceOf(InvalidDataException.class)
                .hasMessage("Latitude and longitude are invalid");
    }
}