package org.kgarbacki.weatherAppApi.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.kgarbacki.weatherAppApi.apiClient.ApiClient;
import org.kgarbacki.weatherAppApi.controller.v1.request.OpenMeteoReq;
import org.kgarbacki.weatherAppApi.controller.v1.request.WeatherReq;
import org.kgarbacki.weatherAppApi.dto.WeatherFields;
import org.kgarbacki.weatherAppApi.dto.WeatherReportDto;
import org.kgarbacki.weatherAppApi.apiClient.response.WeatherForecast;
import org.kgarbacki.weatherAppApi.dto.ReportSummaryDto;
import org.kgarbacki.weatherAppApi.util.SolarPanelsUtil;
import org.kgarbacki.weatherAppApi.util.WeatherUtil;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Slf4j
@Service
@RequiredArgsConstructor
public class WeatherServiceImpl implements WeatherService {
    private final ApiClient apiClient;
    private static final String TIMEZONE = "auto";

    @Override
    public List<WeatherReportDto> getWeatherReport(WeatherReq req) {
        log.info("Fetching Weather Report data for latitude: {} and longitude: {}", req.latitude(), req.longitude());

        List<String> weatherFields = Stream.of(
                WeatherFields.WEATHER_CODE,
                WeatherFields.TEMPERATURE_2M_MAX,
                WeatherFields.TEMPERATURE_2M_MIN,
                WeatherFields.SUNSHINE_DURATION
        ).map(WeatherFields::toString).collect(Collectors.toList());


        WeatherForecast weatherForecast = getWeatherForecast(req, weatherFields);

        return mapToWeatherReportDto(weatherForecast);
    }

    @Override
    public ReportSummaryDto getReportSummary(WeatherReq req) {
        log.info("Fetching Weekly Report Summary data for latitude: {} and longitude: {}", req.latitude(), req.longitude());

        List<String> weatherFields = Stream.of(
                        WeatherFields.SURFACE_PRESSURE_MEAN,
                        WeatherFields.SUNSHINE_DURATION,
                        WeatherFields.TEMPERATURE_2M_MAX,
                        WeatherFields.TEMPERATURE_2M_MIN,
                        WeatherFields.PRECIPITATION_SUM
        ).map(WeatherFields::toString).collect(Collectors.toList());


        WeatherForecast weatherForecast = getWeatherForecast(req, weatherFields);

        return new ReportSummaryDto(
                WeatherUtil.calcAveragePressure(weatherForecast.daily().surface_pressure_mean()),
                WeatherUtil.calcMaxWeeklyTemperature(weatherForecast.daily().temperature_2m_max()),
                WeatherUtil.calcMinWeeklyTemperature(weatherForecast.daily().temperature_2m_min()),
                WeatherUtil.evaluateWeeklyPrecipitation(weatherForecast.daily().precipitation_sum())
        );
    }

    private static List<WeatherReportDto> mapToWeatherReportDto(WeatherForecast weatherForecast) {
        WeatherForecast.DailyData daily = weatherForecast.daily();
        List<WeatherReportDto> reports =  new ArrayList<>();

        for (int i = 0; i < daily.time().size(); i++) {
            reports.add(new WeatherReportDto(
                    daily.time().get(i),
                    daily.weather_code().get(i),
                    daily.temperature_2m_max().get(i),
                    daily.temperature_2m_min().get(i),
                    SolarPanelsUtil.calcGeneratedEnergy(
                            daily.sunshine_duration().get(i)
                    )
            ));
        }

        return reports;
    }

    private WeatherForecast getWeatherForecast(WeatherReq req, List<String> weatherFields) {
        OpenMeteoReq request = new OpenMeteoReq(
                req.latitude(),
                req.longitude(),
                weatherFields,
                TIMEZONE
        );

        return apiClient.getWeatherData(request);
    }
}
