package org.kgarbacki.weatherAppApi.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.kgarbacki.weatherAppApi.apiClient.ApiClient;
import org.kgarbacki.weatherAppApi.apiClient.response.WeatherForecast;
import org.kgarbacki.weatherAppApi.controller.v1.request.OpenMeteoReq;
import org.kgarbacki.weatherAppApi.controller.v1.request.WeatherReq;
import org.kgarbacki.weatherAppApi.dto.ReportSummaryDto;
import org.kgarbacki.weatherAppApi.dto.WeatherReportDto;
import org.kgarbacki.weatherAppApi.util.SolarPanelsUtil;
import org.kgarbacki.weatherAppApi.util.WeatherUtil;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class WeatherServiceImplTest {

    @Mock
    private ApiClient apiClient;
    @InjectMocks
    private WeatherServiceImpl underTest;

    @Test
    void getWeatherReport() {
        // Given
        WeatherReq req = new WeatherReq(52.52f, 13.41f);
        WeatherForecast sampleForecast = getSampleWeatherForecast();

        //When
        when(apiClient.getWeatherData(
                any(OpenMeteoReq.class)
        )).thenReturn(sampleForecast);

        List<WeatherReportDto> result = underTest.getWeatherReport(req);

        // Then
        verify(apiClient).getWeatherData(any(OpenMeteoReq.class));
        assertThat(result).hasSize(2);
        assertThat(result.getFirst()).isEqualTo(getExpectedWeatherReportDto());
    }

    private WeatherReportDto getExpectedWeatherReportDto() {
        return new WeatherReportDto(
                LocalDate.of(2025, 8, 4),
                61,
                21.3f,
                17.0f,
                SolarPanelsUtil.calcGeneratedEnergy(14446.27f)
        );
    }

    @Test
    void getReportSummary() {
        // Given
        WeatherReq req = new WeatherReq(52.52f, 13.41f);
        WeatherForecast sampleForecast = getSampleWeatherForecast();

        //When
        when(apiClient.getWeatherData(
                any(OpenMeteoReq.class)
        )).thenReturn(sampleForecast);

        ReportSummaryDto result = underTest.getReportSummary(req);

        //Then
        verify(apiClient).getWeatherData(any(OpenMeteoReq.class));
        assertThat(result).isEqualTo(getExpectedReportSummaryDto());
    }

    private ReportSummaryDto getExpectedReportSummaryDto() {
        return new ReportSummaryDto(
                WeatherUtil.calcAveragePressure(
                        new ArrayList<>(List.of(1010.3f, 1016.6f))
                ),
                WeatherUtil.calcMaxWeeklyTemperature(
                        new ArrayList<>(List.of(21.3f, 22.1f))
                ),
                WeatherUtil.calcMinWeeklyTemperature(
                        new ArrayList<>(List.of(17.0f, 12.9f))
                ),
                WeatherUtil.evaluateWeeklyPrecipitation(
                        new ArrayList<>(List.of(4.40f, 0.00f))
                )
        );
    }


    private WeatherForecast getSampleWeatherForecast() {
        return new WeatherForecast(
                new WeatherForecast.DailyData(
                        new ArrayList<>(List.of(
                                LocalDate.of(2025, 8, 4),
                                LocalDate.of(2025, 8, 5)
                        )),
                        new ArrayList<>(List.of(61, 3)),
                        new ArrayList<>(List.of(21.3f, 22.1f)),
                        new ArrayList<>(List.of(17.0f, 12.9f)),
                        new ArrayList<>(List.of(14446.27f, 49486.79f)),
                        new ArrayList<>(List.of(4.40f, 0.00f)),
                        new ArrayList<>(List.of(1010.3f, 1016.6f))
                )
        );
    }
}

