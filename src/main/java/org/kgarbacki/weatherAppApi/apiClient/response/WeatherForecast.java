package org.kgarbacki.weatherAppApi.apiClient.response;

import java.time.LocalDate;
import java.util.List;

public record WeatherForecast(
        DailyData daily
) {
    public record DailyData(
            List<LocalDate> time,
            List<Integer> weather_code,
            List<Float> temperature_2m_max,
            List<Float> temperature_2m_min,
            List<Float> sunshine_duration,
            List<Float> precipitation_sum,
            List<Float> surface_pressure_mean

    ) {
    }
}


